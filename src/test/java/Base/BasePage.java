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
    private Waits waits;

    public BasePage() {
        this.driver = DriverManager.getDriver(null);
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver instance is null in BasePage");
        }
        this.waits = new Waits(this.driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void clickButton(String locator) {
//        waits.waitUntil(() -> {
//            WebElement button = driver.findElement(By.xpath(locator));
//            scrollToElement(button);
//            button.click();
//        });
        waits.waitUntil(() -> {
            WebElement button = driver.findElement(By.xpath(locator));
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                return true;
            }
            return false;
        }, "Timeout waiting for button: " + locator);
    }

    public void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

}
