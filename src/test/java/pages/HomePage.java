package pages;

import Base.BasePage;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utility.*;

import java.util.List;
import java.util.NoSuchElementException;

public class HomePage extends BasePage {

    public static String elements_xpath = "//*[@class='card-body']/*[text()='Elements']";
    public static final String MENULIST_XPATH = "//*[@class='menu-list']/li/*[text()='%s']";
    public static final String HOMEPAGE_CARD_XPATH = "//*[@class='card-body']/*[text()='%s']";
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    private Waits waits;

    public HomePage() {
        super();
        this.waits = new Waits();
    }

    public void click_on_card_by_name(String cardName) {
        WebElement element = driver.findElement(By.xpath(String.format(HOMEPAGE_CARD_XPATH, cardName)));
        scrollToElement(element);
        element.click();
    }

    public void click_on_menu_items_from_cards(String menuName) {
        String menuItemXpath = String.format(MENULIST_XPATH, menuName);
        logger.info("Clicking on menu item: " + menuName + " | XPath: " + menuItemXpath);

        List<WebElement> menuItems = driver.findElements(By.xpath(menuItemXpath));

        if (menuItems.isEmpty()) {
            logger.error("Menu item not found: " + menuName);
            throw new NoSuchElementException("Menu item not found: " + menuName);
        }

        clickButton(menuItemXpath);

//        logger.info("Clicking on menu item: " + menuName);
//        clickButton(String.format(MENULIST_XPATH, menuName));
    }


    public void clickOnMenuItem(String menuName) {
        logger.info("Clicking on menu item: " + menuName);
        clickButton(String.format(MENULIST_XPATH, menuName));
    }
}
