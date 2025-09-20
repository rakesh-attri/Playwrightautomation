package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.Log;

import java.util.Map;

/**
 * Test class for Salesforce Login functionality
 */
@Epic("Salesforce Authentication")
@Feature("User Login")
public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    @Description("Test login functionality with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User should be able to login with valid credentials")
    public void testValidLogin(Map<String, String> testData) {
        String testCase = testData.get("TestCase");
        String username = testData.get("Username");
        String password = testData.get("Password");
        String expectedResult = testData.get("ExpectedResult");
        String url = testData.get("URL");

        Log.info("Executing test case: " + testCase);
        Log.info("Username: " + username);
        Log.info("Expected Result: " + expectedResult);

        try {
            // Navigate to login page
            loginPage.navigateToLoginPage(url);
            loginPage.takeScreenshot("login_page_loaded");

            // Perform login
            loginPage.login(username, password);
            loginPage.takeScreenshot("after_login_attempt");

            // Verify login result
            if ("Success".equalsIgnoreCase(expectedResult)) {
                Assert.assertTrue(loginPage.isLoginSuccessful(), 
                    "Login should be successful for valid credentials");
                Log.info("Login test passed for test case: " + testCase);
            } else if ("Failure".equalsIgnoreCase(expectedResult)) {
                Assert.assertTrue(loginPage.isLoginErrorDisplayed(), 
                    "Login error should be displayed for invalid credentials");
                String errorMessage = loginPage.getLoginErrorMessage();
                Log.info("Login error message: " + errorMessage);
                Log.info("Login test passed for test case: " + testCase);
            }

        } catch (Exception e) {
            Log.error("Test case " + testCase + " failed with exception: " + e.getMessage(), e);
            loginPage.takeScreenshot("login_test_failed_" + testCase);
            Assert.fail("Test case " + testCase + " failed: " + e.getMessage());
        }
    }

    @Test
    @Description("Test login page elements visibility")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login page should display all required elements")
    public void testLoginPageElements() {
        String url = "https://test.salesforce.com";
        
        Log.info("Testing login page elements visibility");
        
        try {
            // Navigate to login page
            loginPage.navigateToLoginPage(url);
            loginPage.takeScreenshot("login_page_elements");

            // Verify page elements are visible
            Assert.assertTrue(loginPage.isVisible("input[name='username']"), 
                "Username field should be visible");
            Assert.assertTrue(loginPage.isVisible("input[name='pw']"), 
                "Password field should be visible");
            Assert.assertTrue(loginPage.isVisible("input[name='Login']"), 
                "Login button should be visible");

            Log.info("Login page elements test passed");

        } catch (Exception e) {
            Log.error("Login page elements test failed: " + e.getMessage(), e);
            loginPage.takeScreenshot("login_page_elements_failed");
            Assert.fail("Login page elements test failed: " + e.getMessage());
        }
    }

    @Test
    @Description("Test forgot password functionality")
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to access forgot password link")
    public void testForgotPasswordLink() {
        String url = "https://test.salesforce.com";
        
        Log.info("Testing forgot password link");
        
        try {
            // Navigate to login page
            loginPage.navigateToLoginPage(url);
            
            // Click forgot password link
            loginPage.clickForgotPassword();
            loginPage.takeScreenshot("forgot_password_page");
            
            // Verify navigation to forgot password page
            Assert.assertTrue(loginPage.isVisible("input[name='un']"), 
                "Forgot password page should be loaded");
            
            Log.info("Forgot password link test passed");

        } catch (Exception e) {
            Log.error("Forgot password link test failed: " + e.getMessage(), e);
            loginPage.takeScreenshot("forgot_password_test_failed");
            Assert.fail("Forgot password link test failed: " + e.getMessage());
        }
    }
}
