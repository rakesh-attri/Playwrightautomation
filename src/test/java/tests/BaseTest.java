package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import pages.AccountPage;
import pages.LoginPage;
import utils.Log;

import java.nio.file.Paths;

/**
 * Base test class containing common setup and teardown methods
 */
public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected LoginPage loginPage;
    protected AccountPage accountPage;

    @BeforeSuite
    public void setUpSuite() {
        Log.info("Setting up test suite");
        playwright = Playwright.create();
    }

    @BeforeMethod
    public void setUp() {
        Log.info("Setting up browser and page for test");
        
        // Launch browser
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false) // Set to true for headless execution
                .setSlowMo(1000)); // Add delay for better visibility
        
        // Create browser context
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"));
        
        // Create new page
        page = context.newPage();
        
        // Initialize page objects
        loginPage = new LoginPage(page);
        accountPage = new AccountPage(page);
        
        // Create screenshots directory
        try {
            java.nio.file.Files.createDirectories(Paths.get("screenshots"));
        } catch (Exception e) {
            Log.warn("Could not create screenshots directory: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        Log.info("Tearing down browser and page after test");
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        Log.info("Tearing down test suite");
        if (playwright != null) {
            playwright.close();
        }
    }
}
