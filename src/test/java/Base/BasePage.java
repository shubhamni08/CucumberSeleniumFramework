package Base;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import utility.DriverManager;
import utility.LoggerFactory;
import utility.Waits;

public class BasePage {

    protected WebDriver driver;
    protected JavascriptExecutor jsExecutor;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private final Waits waits;

    public BasePage() {
        this.driver = DriverManager.getDriver(null);
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver instance is null in BasePage");
        }
        this.waits = new Waits(this.driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void clickButton(String locator) {
        waits.waitUntil(() -> {
            try {
                WebElement button = driver.findElement(By.xpath(locator));
                scrollToElement(button);
                if (button.isDisplayed() && button.isEnabled()) {
                    waits.waitForElementToBeClickable(button); // Ensure element is clickable
                    button.click();
                    logger.info("Clicked button: {}", locator);
                    return true;
                } else {
                    logger.error("Button not clickable: {}", locator);
                    return false;
                }
            } catch (Exception e) {
                logger.error("Error clicking button with locator: {}. Exception: {}", locator, e.getMessage());
                return false; // retry or fail gracefully
            }
        }, "Timeout waiting for button: " + locator);
    }

    public void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickUsingJS(WebElement element){
        jsExecutor.executeScript(("arguments[0].click();"), element);
    }


}
