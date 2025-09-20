package pages;

import com.microsoft.playwright.Page;
import utils.Log;

/**
 * Page Object Model for Salesforce Account Page
 */
public class AccountPage extends BasePage {
    
    // Locators for Salesforce Lightning Account Page
    private static final String APP_LAUNCHER = "div[class*='slds-icon-waffle']";
    private static final String ACCOUNTS_TAB = "a[data-label='Accounts']";
    private static final String NEW_BUTTON = "div[title='New']";
    private static final String ACCOUNT_NAME_INPUT = "input[placeholder='Account Name']";
    private static final String ACCOUNT_TYPE_DROPDOWN = "lightning-combobox[data-field-name='Type']";
    private static final String ACCOUNT_TYPE_OPTION = "lightning-base-combobox-item[data-value='Customer - Direct']";
    private static final String ACCOUNT_INDUSTRY_DROPDOWN = "lightning-combobox[data-field-name='Industry']";
    private static final String ACCOUNT_INDUSTRY_OPTION = "lightning-base-combobox-item[data-value='Technology']";
    private static final String ACCOUNT_PHONE_INPUT = "input[placeholder='Phone']";
    private static final String ACCOUNT_WEBSITE_INPUT = "input[placeholder='Website']";
    private static final String SAVE_BUTTON = "button[name='SaveEdit']";
    private static final String SAVE_AND_NEW_BUTTON = "button[name='SaveAndNew']";
    private static final String CANCEL_BUTTON = "button[name='CancelEdit']";
    private static final String ACCOUNT_DETAIL_TITLE = "h1[class*='slds-page-header__title']";
    private static final String SUCCESS_MESSAGE = "div[class*='slds-notify__content']";
    private static final String ACCOUNT_NAME_DISPLAY = "span[data-field-name='Name']";
    private static final String ACCOUNT_TYPE_DISPLAY = "span[data-field-name='Type']";
    private static final String ACCOUNT_INDUSTRY_DISPLAY = "span[data-field-name='Industry']";
    private static final String ACCOUNT_PHONE_DISPLAY = "span[data-field-name='Phone']";
    private static final String ACCOUNT_WEBSITE_DISPLAY = "span[data-field-name='Website']";

    public AccountPage(Page page) {
        super(page);
    }

    /**
     * Navigate to Accounts tab
     */
    public void navigateToAccounts() {
        Log.info("Navigating to Accounts tab");
        try {
            // Click on App Launcher if visible
            if (isVisible(APP_LAUNCHER)) {
                click(APP_LAUNCHER);
                waitForElement(ACCOUNTS_TAB);
            }
            click(ACCOUNTS_TAB);
            waitForPageLoad();
        } catch (Exception e) {
            Log.error("Error navigating to Accounts: " + e.getMessage());
            // Try direct navigation
            page.navigate("lightning/o/Account/list");
            waitForPageLoad();
        }
    }

    /**
     * Click New button to create new account
     */
    public void clickNewButton() {
        Log.info("Clicking New button to create new account");
        waitForElement(NEW_BUTTON);
        click(NEW_BUTTON);
        waitForPageLoad();
    }

    /**
     * Enter account name
     */
    public void enterAccountName(String accountName) {
        Log.info("Entering account name: " + accountName);
        fill(ACCOUNT_NAME_INPUT, accountName);
    }

    /**
     * Select account type
     */
    public void selectAccountType(String accountType) {
        Log.info("Selecting account type: " + accountType);
        click(ACCOUNT_TYPE_DROPDOWN);
        waitForElement(ACCOUNT_TYPE_OPTION);
        click(ACCOUNT_TYPE_OPTION);
    }

    /**
     * Select account industry
     */
    public void selectAccountIndustry(String industry) {
        Log.info("Selecting account industry: " + industry);
        click(ACCOUNT_INDUSTRY_DROPDOWN);
        waitForElement(ACCOUNT_INDUSTRY_OPTION);
        click(ACCOUNT_INDUSTRY_OPTION);
    }

    /**
     * Enter account phone
     */
    public void enterAccountPhone(String phone) {
        Log.info("Entering account phone: " + phone);
        fill(ACCOUNT_PHONE_INPUT, phone);
    }

    /**
     * Enter account website
     */
    public void enterAccountWebsite(String website) {
        Log.info("Entering account website: " + website);
        fill(ACCOUNT_WEBSITE_INPUT, website);
    }

    /**
     * Click Save button
     */
    public void clickSaveButton() {
        Log.info("Clicking Save button");
        click(SAVE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click Save and New button
     */
    public void clickSaveAndNewButton() {
        Log.info("Clicking Save and New button");
        click(SAVE_AND_NEW_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click Cancel button
     */
    public void clickCancelButton() {
        Log.info("Clicking Cancel button");
        click(CANCEL_BUTTON);
    }

    /**
     * Create new account with all details
     */
    public void createAccount(String accountName, String accountType, String industry, String phone, String website) {
        Log.info("Creating new account with name: " + accountName);
        enterAccountName(accountName);
        selectAccountType(accountType);
        selectAccountIndustry(industry);
        enterAccountPhone(phone);
        enterAccountWebsite(website);
        clickSaveButton();
    }

    /**
     * Check if account was created successfully
     */
    public boolean isAccountCreatedSuccessfully() {
        Log.info("Checking if account was created successfully");
        try {
            // Wait for account detail page to load
            waitForElement(ACCOUNT_DETAIL_TITLE);
            return isVisible(ACCOUNT_DETAIL_TITLE) || isVisible(SUCCESS_MESSAGE);
        } catch (Exception e) {
            Log.error("Account creation verification failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get account name from detail page
     */
    public String getAccountName() {
        Log.info("Getting account name from detail page");
        if (isVisible(ACCOUNT_NAME_DISPLAY)) {
            return getText(ACCOUNT_NAME_DISPLAY);
        }
        return "";
    }

    /**
     * Get account type from detail page
     */
    public String getAccountType() {
        Log.info("Getting account type from detail page");
        if (isVisible(ACCOUNT_TYPE_DISPLAY)) {
            return getText(ACCOUNT_TYPE_DISPLAY);
        }
        return "";
    }

    /**
     * Get account industry from detail page
     */
    public String getAccountIndustry() {
        Log.info("Getting account industry from detail page");
        if (isVisible(ACCOUNT_INDUSTRY_DISPLAY)) {
            return getText(ACCOUNT_INDUSTRY_DISPLAY);
        }
        return "";
    }

    /**
     * Get account phone from detail page
     */
    public String getAccountPhone() {
        Log.info("Getting account phone from detail page");
        if (isVisible(ACCOUNT_PHONE_DISPLAY)) {
            return getText(ACCOUNT_PHONE_DISPLAY);
        }
        return "";
    }

    /**
     * Get account website from detail page
     */
    public String getAccountWebsite() {
        Log.info("Getting account website from detail page");
        if (isVisible(ACCOUNT_WEBSITE_DISPLAY)) {
            return getText(ACCOUNT_WEBSITE_DISPLAY);
        }
        return "";
    }

    /**
     * Verify account details match expected values
     */
    public boolean verifyAccountDetails(String expectedName, String expectedType, String expectedIndustry, 
                                      String expectedPhone, String expectedWebsite) {
        Log.info("Verifying account details");
        boolean nameMatch = getAccountName().equals(expectedName);
        boolean typeMatch = getAccountType().equals(expectedType);
        boolean industryMatch = getAccountIndustry().equals(expectedIndustry);
        boolean phoneMatch = getAccountPhone().equals(expectedPhone);
        boolean websiteMatch = getAccountWebsite().equals(expectedWebsite);
        
        Log.info("Account verification - Name: " + nameMatch + ", Type: " + typeMatch + 
                ", Industry: " + industryMatch + ", Phone: " + phoneMatch + ", Website: " + websiteMatch);
        
        return nameMatch && typeMatch && industryMatch && phoneMatch && websiteMatch;
    }
}
