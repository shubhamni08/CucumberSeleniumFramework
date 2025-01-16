package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends BaseTest {

    public BasePage() {
    }

    public void click_on_menu_items_from_cards(String Name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        System.out.println("click_on_cards_by_elements_page method: "+Name);
        super.clickButton(String.format(menulistXpath, Name));
    }
}
