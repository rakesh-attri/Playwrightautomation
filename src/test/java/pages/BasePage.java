package pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import utils.Log;

/**
 * Base page class containing common functionality for all page objects
 */
public class BasePage {
    protected Page page;
    protected BrowserContext context;

    public BasePage(Page page) {
        this.page = page;
        this.context = page.context();
    }

    /**
     * Wait for element to be visible
     */
    protected void waitForElement(String selector) {
        Log.info("Waiting for element: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(10000));
    }

    /**
     * Click on element with wait
     */
    protected void click(String selector) {
        Log.info("Clicking on element: " + selector);
        waitForElement(selector);
        page.click(selector);
    }

    /**
     * Fill input field with text
     */
    protected void fill(String selector, String text) {
        Log.info("Filling field " + selector + " with: " + text);
        waitForElement(selector);
        page.fill(selector, text);
    }

    /**
     * Get text from element
     */
    protected String getText(String selector) {
        Log.info("Getting text from element: " + selector);
        waitForElement(selector);
        return page.textContent(selector);
    }

    /**
     * Check if element is visible
     */
    public boolean isVisible(String selector) {
        try {
            return page.isVisible(selector);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        Log.info("Waiting for page to load completely");
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Take screenshot
     */
    public void takeScreenshot(String name) {
        Log.info("Taking screenshot: " + name);
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("screenshots/" + name + ".png")));
    }
}
