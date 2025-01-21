package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Waits {

    private final WebDriver driver;

    private final WebDriverWait wait;

    public Waits() {
        WebDriver driver = DriverManager.getDriver(null);
        if (driver == null) {
            throw new IllegalStateException("WebDriver is NULL in Waits default constructor! Ensure Hooks sets it up first.");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public Waits(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null in Waits class");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public void waitUntil(Supplier<Boolean> condition, String errorMessage) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null when calling waitUntil()");
        }
        try {
            wait.until(driver -> condition.get());
        } catch (Exception e) {
            throw new AssertionError(errorMessage, e);
        }
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
        wait.until(ExpectedConditions.numberOfWindowsToBe(expectedwindow));
    }

    public boolean waitForTextOfElementPresent(By locator, String text){
       return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForAttributeToBe(WebElement element, String val1, String val2){
        return wait.until(ExpectedConditions.attributeToBe(element, val1, val2));
    }

    public void waitForElementToBeInvisible(WebElement element) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
//        } catch (Exception e) {
//            // Log or handle timeout exception if necessary
//            System.err.println("Element was not invisible in the specified time: " + e.getMessage());
//        }
    }

}
