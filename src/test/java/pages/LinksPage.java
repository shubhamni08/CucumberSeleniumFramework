package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class LinksPage extends BaseTest {
    public LinksPage() {
    }

    public static String linksXpath = "//*[text()='%s']";

    public static String responseCodeXpath = "//*[@id='linkResponse']";

    public String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    public void clickLink(String linkName) {
        WebElement element = driver.findElement(By.xpath(String.format(linksXpath, linkName)));
        element.click();
        System.out.println("clickLink method successful");
        // Handle new tab switch
//        Set<String> windowHandles = driver.getWindowHandles();
//        Iterator<String> iterator = windowHandles.iterator();
//        String parentTab = iterator.next(); // Current (original) tab
//        String newTab = iterator.next();   // Newly opened tab
//
//        // Switch to the new tab
//        driver.switchTo().window(newTab);
    }

    public String getAPIResponseText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element =  driver.findElement(By.xpath(responseCodeXpath));
        String response = element.getText();
        System.out.println("getAPIResponseText method successful: "+response);
        return response;
    }
}
