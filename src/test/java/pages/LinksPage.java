package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class LinksPage extends BaseTest {
    public LinksPage() {
    }

    public static String linksXpath = "//*[text()='%s']";

    public static String responseCodeXpath = "//*[@id='linkResponse']";

    public WebDriverWait wait;

    public String getCurrentURL(){
        // Store the parent window handle
        String parentWindow = driver.getWindowHandle();

        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //switch to the new window
        Set<String> allWindows  = driver.getWindowHandles();

        for(String window:allWindows ){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }

        // Assert the current URL
        return driver.getCurrentUrl();
    }

    public void clickLink(String linkName) {
        WebElement element = driver.findElement(By.xpath(String.format(linksXpath, linkName)));
        element.click();
    }

    public String getAPIResponseText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(responseCodeXpath)));
        String response = element.getText();
        System.out.println("getAPIResponseText method successful: "+response);
        return response;
    }
}
