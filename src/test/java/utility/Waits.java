package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class Waits {

    private WebDriver driver;

    private WebDriverWait wait;

    public Waits() {
        this.wait = new WebDriverWait(DriverManager.getDriver(null), Duration.ofSeconds(20));
    }

    public void waitForElement(WebElement element) {
        //Generic wait script which can be used for all elements.
        //If you want to wait for more than 10 seconds, you can use the function beneath this one.
        waitForElement(element, Duration.ofSeconds(10));
    }

    public void waitForElement(WebElement element, Duration seconds) {
        //Overloading of method waitForElement, when you know an element takes more then 10 seconds to load.
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            //exception handling: generate clean error message
            Assert.fail("Element not found on page: " + element);
        }
    }

    public void waitForElements(List<WebElement> elementList){
        //Generic wait script which can be used for all list elements.
        //Waits for max 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e ){
            Assert.fail("Element not found on page: " + elementList);
        }
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

    public WebElement waitForElementVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForVisiblityOfElement(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToBeClickable(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button));
    }

    public WebElement waitForElementToBePresence(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Alert waitForAlertPresence() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForCondition(Function<WebDriver, Boolean> condition) {
        wait.until(condition);
    }

    public void waitForNumberOfWindow(int expectedwindow){
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    public boolean waitForTextOfElementPresent(By locator, String text){
       return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForAttributeToBe(WebElement element, String val1, String val2){
        return wait.until(ExpectedConditions.attributeToBe(element, val1, val2));
    }

}
