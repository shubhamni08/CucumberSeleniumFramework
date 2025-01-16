package pages;

import Base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BaseTest {

    public static String elements_xpath = "//*[@class='card-body']/*[text()='Elements']";
//    public static String forms_xpath = "//*[@class='card-body']/*[text()='Forms']";
//    public static String alerts_frames_windows_xpath = "//*[@class='card-body']/*[text()='Alerts, Frame & Windows']";
//    public static String book_store_application_xpath = "//*[@class='card-body']/*[text()='Book Store Application']";

    public HomePage() {
        driver.get("https://demoqa.com/");
    }

    public void click_elements_card(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = driver.findElement(By.xpath(elements_xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    public void click_on_card_by_name(String cardName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = driver.findElement(By.xpath(String.format(homePageCardXpath, cardName)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

}
