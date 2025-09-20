package utils;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from CSV files
 */
public class CSVUtils {
    private static final String TEST_DATA_PATH = "testData/";

    /**
     * Reads test data from CSV file and returns as Object array for TestNG DataProvider
     */
    public static Object[][] getTestDataFromCSV(String fileName) {
        List<Map<String, String>> testDataList = new ArrayList<>();
        String filePath = TEST_DATA_PATH + fileName;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = null;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                
                if (lineNumber == 1) {
                    // First line contains headers
                    headers = values;
                } else {
                    // Data rows
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < headers.length && i < values.length; i++) {
                        rowData.put(headers[i].trim(), values[i].trim());
                    }
                    testDataList.add(rowData);
                }
            }
            
        } catch (IOException e) {
            Log.error("Error reading CSV file: " + e.getMessage(), e);
        }

        // Convert List to Object[][]
        Object[][] testData = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            testData[i][0] = testDataList.get(i);
        }

        return testData;
    }

    /**
     * DataProvider for login test data from CSV
     */
    @DataProvider(name = "loginDataCSV")
    public static Object[][] getLoginDataFromCSV() {
        return getTestDataFromCSV("LoginData.csv");
    }

    /**
     * DataProvider for account test data from CSV
     */
    @DataProvider(name = "accountDataCSV")
    public static Object[][] getAccountDataFromCSV() {
        return getTestDataFromCSV("AccountData.csv");
    }
}
