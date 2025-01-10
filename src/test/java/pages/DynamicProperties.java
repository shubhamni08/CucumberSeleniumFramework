package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DynamicProperties extends BaseTest {
    public DynamicProperties(){
    }

    public static String dynamicButtonXpath = "//*[text()='%s']";

    public static String colorChangeButtonXpath = "//*[@class='mt-4 text-danger btn btn-primary']";

    private WebDriverWait wait;

    public boolean buttonEnabledVisible(String btn){
        try {
            if(btn.equals("Will enable 5 seconds")){
                WebElement button = driver.findElement(By.xpath(String.format(dynamicButtonXpath,btn)));
                wait = new WebDriverWait(driver, Duration.ofSeconds(6)); // Adding buffer for 5 seconds
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                wait.until(ExpectedConditions.elementToBeClickable(button));
                return button.isEnabled();
            }else if(btn.equals("Visible After 5 Seconds")){
                wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adding buffer for 5 seconds
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(dynamicButtonXpath,btn))));
                WebElement button = driver.findElement(By.xpath(String.format(dynamicButtonXpath,btn)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                return button.isDisplayed();
            }
        } catch (TimeoutException e) {
            throw new AssertionError(btn + " button did not become visible after 5 seconds", e);
        }
        return false;
    }

    public void btnChangeColor(String buttonName){
        WebElement button = driver.findElement(By.xpath(String.format(dynamicButtonXpath,buttonName)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        // Get the initial color of the button
        String initialColor = button.getCssValue("color");
        System.out.println("Initial color: " + initialColor);

        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(driver -> {
            String currentColor = button.getCssValue("color");
            return !currentColor.equals(initialColor);
        });

        String newColor = button.getCssValue("color");
        System.out.println("New color: " + newColor);

        Assert.assertNotEquals(initialColor, newColor, buttonName + " button color did not change as expected.");
    }
}
