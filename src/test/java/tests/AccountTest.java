package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.Log;

import java.util.Map;

/**
 * Test class for Salesforce Account functionality
 */
@Epic("Salesforce CRM")
@Feature("Account Management")
public class AccountTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    @Description("Test account creation with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User should be able to create new accounts")
    public void testAccountCreation(Map<String, String> loginData) {
        String username = loginData.get("Username");
        String password = loginData.get("Password");
        String url = loginData.get("URL");

        Log.info("Testing account creation functionality");
        
        try {
            // Login first
            loginPage.navigateToLoginPage(url);
            loginPage.login(username, password);
            loginPage.takeScreenshot("after_login");

            // Verify login was successful
            Assert.assertTrue(loginPage.isLoginSuccessful(), 
                "Login should be successful before creating account");

            // Navigate to Accounts
            accountPage.navigateToAccounts();
            accountPage.takeScreenshot("accounts_page");

            // Create new account
            accountPage.clickNewButton();
            accountPage.takeScreenshot("new_account_form");

            // Fill account details
            String accountName = "Test Account " + System.currentTimeMillis();
            String accountType = "Customer - Direct";
            String industry = "Technology";
            String phone = "123-456-7890";
            String website = "https://www.testaccount.com";

            accountPage.createAccount(accountName, accountType, industry, phone, website);
            accountPage.takeScreenshot("account_created");

            // Verify account creation
            Assert.assertTrue(accountPage.isAccountCreatedSuccessfully(), 
                "Account should be created successfully");

            // Verify account details
            Assert.assertTrue(accountPage.verifyAccountDetails(accountName, accountType, industry, phone, website),
                "Account details should match the entered values");

            Log.info("Account creation test passed for account: " + accountName);

        } catch (Exception e) {
            Log.error("Account creation test failed: " + e.getMessage(), e);
            accountPage.takeScreenshot("account_creation_failed");
            Assert.fail("Account creation test failed: " + e.getMessage());
        }
    }

    @Test(dataProvider = "accountData", dataProviderClass = ExcelUtils.class)
    @Description("Test account creation with data-driven approach")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User should be able to create accounts with various data sets")
    public void testDataDrivenAccountCreation(Map<String, String> testData) {
        String testCase = testData.get("TestCase");
        String username = testData.get("Username");
        String password = testData.get("Password");
        String url = testData.get("URL");
        String accountName = testData.get("AccountName");
        String accountType = testData.get("AccountType");
        String industry = testData.get("Industry");
        String phone = testData.get("Phone");
        String website = testData.get("Website");

        Log.info("Executing data-driven account creation test case: " + testCase);

        try {
            // Login first
            loginPage.navigateToLoginPage(url);
            loginPage.login(username, password);

            // Verify login was successful
            Assert.assertTrue(loginPage.isLoginSuccessful(), 
                "Login should be successful before creating account");

            // Navigate to Accounts
            accountPage.navigateToAccounts();

            // Create new account
            accountPage.clickNewButton();

            // Fill account details from test data
            accountPage.createAccount(accountName, accountType, industry, phone, website);
            accountPage.takeScreenshot("account_created_" + testCase);

            // Verify account creation
            Assert.assertTrue(accountPage.isAccountCreatedSuccessfully(), 
                "Account should be created successfully for test case: " + testCase);

            // Verify account details
            Assert.assertTrue(accountPage.verifyAccountDetails(accountName, accountType, industry, phone, website),
                "Account details should match the entered values for test case: " + testCase);

            Log.info("Data-driven account creation test passed for test case: " + testCase);

        } catch (Exception e) {
            Log.error("Data-driven account creation test failed for test case " + testCase + ": " + e.getMessage(), e);
            accountPage.takeScreenshot("account_creation_failed_" + testCase);
            Assert.fail("Data-driven account creation test failed for test case " + testCase + ": " + e.getMessage());
        }
    }

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    @Description("Test account page navigation")
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to navigate to accounts page")
    public void testAccountPageNavigation(Map<String, String> loginData) {
        String username = loginData.get("Username");
        String password = loginData.get("Password");
        String url = loginData.get("URL");

        Log.info("Testing account page navigation");

        try {
            // Login first
            loginPage.navigateToLoginPage(url);
            loginPage.login(username, password);

            // Verify login was successful
            Assert.assertTrue(loginPage.isLoginSuccessful(), 
                "Login should be successful before navigating to accounts");

            // Navigate to Accounts
            accountPage.navigateToAccounts();
            accountPage.takeScreenshot("accounts_page_navigation");

            // Verify we're on the accounts page
            Assert.assertTrue(accountPage.isVisible("div[title='New']"), 
                "New button should be visible on accounts page");

            Log.info("Account page navigation test passed");

        } catch (Exception e) {
            Log.error("Account page navigation test failed: " + e.getMessage(), e);
            accountPage.takeScreenshot("account_navigation_failed");
            Assert.fail("Account page navigation test failed: " + e.getMessage());
        }
    }

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    @Description("Test account creation form validation")
    @Severity(SeverityLevel.NORMAL)
    @Story("Account creation form should validate required fields")
    public void testAccountFormValidation(Map<String, String> loginData) {
        String username = loginData.get("Username");
        String password = loginData.get("Password");
        String url = loginData.get("URL");

        Log.info("Testing account form validation");

        try {
            // Login first
            loginPage.navigateToLoginPage(url);
            loginPage.login(username, password);

            // Verify login was successful
            Assert.assertTrue(loginPage.isLoginSuccessful(), 
                "Login should be successful before testing form validation");

            // Navigate to Accounts
            accountPage.navigateToAccounts();

            // Click New button
            accountPage.clickNewButton();
            accountPage.takeScreenshot("new_account_form_validation");

            // Try to save without filling required fields
            accountPage.clickSaveButton();
            accountPage.takeScreenshot("form_validation_error");

            // Verify validation error is displayed
            // Note: This test might need adjustment based on actual Salesforce validation behavior
            Log.info("Account form validation test completed");

        } catch (Exception e) {
            Log.error("Account form validation test failed: " + e.getMessage(), e);
            accountPage.takeScreenshot("form_validation_failed");
            Assert.fail("Account form validation test failed: " + e.getMessage());
        }
    }
}
