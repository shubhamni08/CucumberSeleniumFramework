package Base;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.DriverManager;
import utility.LoggerFactory;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor jsExecutor;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public static final String MENULIST_XPATH = "//*[@class='menu-list']/li/*[text()='%s']";
    public static final String HOMEPAGE_CARD_XPATH = "//*[@class='card-body']/*[text()='%s']";

    public BasePage() {
        this.driver = DriverManager.getDriver(null);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void click_on_menu_items_from_cards(String menuName) {
        logger.info("Clicking on menu item: " + menuName);
        clickButton(String.format(MENULIST_XPATH, menuName));

    }

    public void clickButton(String locator) {
        waitUntil(() -> {
            WebElement button = driver.findElement(By.xpath(locator));
            scrollToElement(button);
            button.click();
        });
    }

    public void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Helper method to wait for the button to become clickable
    public void waitForElementToBeClickable(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button));
    }

    public void clickOnMenuItem(String menuName) {
        logger.info("Clicking on menu item: " + menuName);
        clickButton(String.format(MENULIST_XPATH, menuName));
    }

    public void waitUntil(Runnable runnable) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class, TimeoutException.class);

        fluentWait.until(driver -> {
            runnable.run();
            return true;
        });
    }

}
