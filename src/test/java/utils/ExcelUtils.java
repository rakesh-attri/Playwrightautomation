package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from Excel files
 */
public class ExcelUtils {
    private static final String TEST_DATA_PATH = "testData/TestData.xlsx";

    /**
     * Reads test data from Excel file and returns as Object array for TestNG DataProvider
     */
    public static Object[][] getTestData(String sheetName) {
        List<Map<String, String>> testDataList = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                Log.error("Sheet '" + sheetName + "' not found in Excel file");
                return new Object[0][0];
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                Log.error("Header row not found in sheet '" + sheetName + "'");
                return new Object[0][0];
            }

            // Get column names
            List<String> columnNames = new ArrayList<>();
            for (Cell cell : headerRow) {
                columnNames.add(getCellValueAsString(cell));
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < columnNames.size(); j++) {
                        Cell cell = row.getCell(j);
                        String columnName = columnNames.get(j);
                        String cellValue = getCellValueAsString(cell);
                        rowData.put(columnName, cellValue);
                    }
                    testDataList.add(rowData);
                }
            }

        } catch (IOException e) {
            Log.error("Error reading Excel file: " + e.getMessage(), e);
        }

        // Convert List to Object[][]
        Object[][] testData = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            testData[i][0] = testDataList.get(i);
        }

        return testData;
    }

    /**
     * Gets cell value as string regardless of cell type
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * DataProvider for login test data
     */
    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return getTestData("LoginData");
    }

    /**
     * DataProvider for account test data
     */
    @DataProvider(name = "accountData")
    public static Object[][] getAccountData() {
        return getTestData("AccountData");
    }
}
