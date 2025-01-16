package pages;

import Base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;

public class RadioButtonPage extends BaseTest {

    public RadioButtonPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    Actions action;

    public static String radioButtonXpath = "//*[@name='like']/following-sibling::label[text()='%s']";
//
//    public static String successMessageXpath = "//span[@class='text-success']";

    public static String noRadioButtonXpath = "//label[@for='noRadio']";

    public static String buttonXpath = "//*[@type='button' and text()='%s']";

    private static final String SELECTED_MESSAGE_XPATH = "//span[@class='text-success']";


    public final WebDriverWait wait;

    public void selectRadioButtonOption(String option){
        // Define the XPath for the radio button based on the common XPath format
        String optionXpath = String.format(radioButtonXpath, option);

        // Wait for the radio button to be visible first, then clickable
        WebElement radioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));

        // Check if the radio button is disabled
        if (radioButton.getAttribute("disabled") != null) {
            System.out.println("The '" + option + "' radio button is disabled and cannot be selected.");
        } else {
            try {
                // Wait for the radio button to be clickable
                radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));

                // Scroll the element into view if it's out of the viewport
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radioButton);

                // Retry the click in case it was blocked by an overlay
                radioButton.click();
                System.out.println(option + " radio button clicked successfully.");
            } catch (ElementClickInterceptedException e) {
                // If click is intercepted, try clicking via JavaScript
                System.out.println("Click intercepted, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioButton);
                System.out.println(option + " radio button clicked using JavaScript.");
            } catch (TimeoutException e) {
                System.out.println("Timeout: The '" + option + "' radio button is not clickable.");
                throw e; // Rethrow the exception after logging
            }
        }
    }

    public boolean isNoRadioButtonDisabled() {
        System.out.println("No Radio Button is disabled");
        return  driver.findElement(By.xpath(noRadioButtonXpath)).getAttribute("class").contains("disabled");
    }

    public void clickButton(String buttonName) {
        System.out.println("click Button method:"+buttonName);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(buttonXpath, buttonName))));
        action = new Actions(driver);
        // Scroll element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        switch (buttonName){
            case "Double Click Me":
                action.doubleClick(element).perform();
                break;
            case "Right Click Me":
                action.contextClick(element).perform();
                break;
            case "Click Me":
                element.click();
                break;
            default:
                throw new IllegalArgumentException("Invalid button name: " + buttonName);
        }
        waitForMessage(buttonName);
    }

    public void waitForMessage(String buttonName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String expectedMessage = "";

        switch (buttonName) {
            case "Double Click Me":
                expectedMessage = "You have done a double click";
                break;
            case "Right Click Me":
                expectedMessage = "You have done a right click";
                break;
            case "Click Me":
                expectedMessage = "You have done a dynamic click";
                break;
        }

        String actualMessage = getClickMessage(expectedMessage);

        if (!actualMessage.equals(expectedMessage)) {
            throw new AssertionError("Expected message '" + expectedMessage + "' but found '" + actualMessage + "'");
        }
    }

    public String getClickMessage(String expectedMessage){
        // Map of messages to corresponding XPaths
        Map<String, String> messageToXpathMap = Map.of(
                "You have done a double click", "//*[@id='doubleClickMessage']",
                "You have done a right click", "//*[@id='rightClickMessage']",
                "You have done a dynamic click", "//*[@id='dynamicClickMessage']",
                "Yes", "//*[@class='text-success']",
                "Impressive", "//*[@class='text-success']"
        );

        // Get the XPath from the map based on the expected message
        String xpath = messageToXpathMap.get(expectedMessage);

        // Check if the XPath was found for the expected message
        if (xpath == null) {
            throw new IllegalArgumentException("Invalid expected message: " + expectedMessage);
        }
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            String message = element.getText();
            System.out.println("Expected: " + expectedMessage + " | Found: " + message);
            return message;
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Message element not found for: " + expectedMessage);
        }

    }
}
