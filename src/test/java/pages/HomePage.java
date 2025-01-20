package pages;

import Base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public static String elements_xpath = "//*[@class='card-body']/*[text()='Elements']";

    public HomePage() {
        driver.get("https://demoqa.com/");
    }

    public void click_elements_card(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = driver.findElement(By.xpath(elements_xpath));
        scrollToElement(element);
        element.click();
    }

    public void click_on_card_by_name(String cardName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = driver.findElement(By.xpath(String.format(HOMEPAGE_CARD_XPATH, cardName)));
        scrollToElement(element);
        element.click();
    }

}
