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
//    public static final String MENULIST_XPATH = "//*[@class='menu-list']/li/*[text()='%s']";
//    public static final String HOMEPAGE_CARD_XPATH = "//*[@class='card-body']/*[text()='%s']";

    public BasePage() {
        this.driver = DriverManager.getDriver(null);
        this.waits = new Waits();
        this.jsExecutor = (JavascriptExecutor) driver;
    }

//    public void click_on_menu_items_from_cards(String menuName) {
//        logger.info("Clicking on menu item: " + menuName);
//        clickButton(String.format(MENULIST_XPATH, menuName));
//    }

    public void clickButton(String locator) {
        waits.waitUntil(() -> {
            WebElement button = driver.findElement(By.xpath(locator));
            scrollToElement(button);
            button.click();
        });
    }

    public void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

//    public void clickOnMenuItem(String menuName) {
//        logger.info("Clicking on menu item: " + menuName);
//        clickButton(String.format(MENULIST_XPATH, menuName));
//    }



}
