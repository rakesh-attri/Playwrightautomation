package pages;

import com.microsoft.playwright.Page;
import utils.Log;

/**
 * Page Object Model for Salesforce Login Page
 */
public class LoginPage extends BasePage {
    
    // Locators for Salesforce Lightning Login Page
    private static final String USERNAME_INPUT = "input[name='username']";
    private static final String PASSWORD_INPUT = "input[name='pw']";
    private static final String LOGIN_BUTTON = "input[name='Login']";
    private static final String REMEMBER_ME_CHECKBOX = "input[name='rememberUn']";
    private static final String FORGOT_PASSWORD_LINK = "a[id='forgot_password_link']";
    private static final String LOGIN_ERROR_MESSAGE = "div[id='error']";
    private static final String LIGHTNING_APP_LAUNCHER = "div[class*='slds-icon-waffle']";
    private static final String USER_MENU = "div[class*='slds-global-header__item'] button[class*='slds-button']";

    public LoginPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Salesforce login page
     */
    public void navigateToLoginPage(String url) {
        Log.info("Navigating to Salesforce login page: " + url);
        page.navigate(url);
        waitForPageLoad();
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        Log.info("Entering username: " + username);
        fill(USERNAME_INPUT, username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        Log.info("Entering password");
        fill(PASSWORD_INPUT, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        Log.info("Clicking login button");
        click(LOGIN_BUTTON);
        waitForPageLoad();
    }

    /**
     * Check remember me checkbox
     */
    public void checkRememberMe() {
        Log.info("Checking remember me checkbox");
        if (isVisible(REMEMBER_ME_CHECKBOX)) {
            click(REMEMBER_ME_CHECKBOX);
        }
    }

    /**
     * Perform complete login
     */
    public void login(String username, String password) {
        Log.info("Performing login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        checkRememberMe();
        clickLoginButton();
    }

    /**
     * Check if login was successful by looking for Lightning app launcher
     */
    public boolean isLoginSuccessful() {
        Log.info("Checking if login was successful");
        try {
            // Wait for Lightning interface to load
            waitForElement(LIGHTNING_APP_LAUNCHER);
            return isVisible(LIGHTNING_APP_LAUNCHER);
        } catch (Exception e) {
            Log.error("Login verification failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if login error message is displayed
     */
    public boolean isLoginErrorDisplayed() {
        Log.info("Checking for login error message");
        return isVisible(LOGIN_ERROR_MESSAGE);
    }

    /**
     * Get login error message text
     */
    public String getLoginErrorMessage() {
        Log.info("Getting login error message");
        if (isLoginErrorDisplayed()) {
            return getText(LOGIN_ERROR_MESSAGE);
        }
        return "";
    }

    /**
     * Check if user menu is visible (alternative success indicator)
     */
    public boolean isUserMenuVisible() {
        Log.info("Checking if user menu is visible");
        return isVisible(USER_MENU);
    }

    /**
     * Click forgot password link
     */
    public void clickForgotPassword() {
        Log.info("Clicking forgot password link");
        click(FORGOT_PASSWORD_LINK);
    }
}
