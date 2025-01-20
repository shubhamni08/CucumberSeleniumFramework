package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utility.LoggerFactory;
import java.util.Map;
import java.util.NoSuchElementException;

public class RadioButtonPage extends BasePage {

    public RadioButtonPage() {
        super();
    }

    Actions action;

    public static String radioButtonXpath = "//*[@name='like']/following-sibling::label[text()='%s']";

    public static String noRadioButtonXpath = "//label[@for='noRadio']";

    public static String buttonXpath = "//*[@type='button' and text()='%s']";

    private static final String SELECTED_MESSAGE_XPATH = "//span[@class='text-success']";

    private static Logger logger = LoggerFactory.getLogger(RadioButtonPage.class);

    public void selectRadioButtonOption(String option){
        // Define the XPath for the radio button based on the common XPath format
        String optionXpath = String.format(radioButtonXpath, option);

        // Wait for the radio button to be visible first, then clickable
        WebElement radioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));

        // Check if the radio button is disabled
        if (radioButton.getAttribute("disabled") != null) {
            logger.info("The '" + option + "' radio button is disabled and cannot be selected.");
        } else {
            try {
                // Wait for the radio button to be clickable
                radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));

                // Scroll the element into view if it's out of the viewport
                scrollToElement(radioButton);

                // Retry the click in case it was blocked by an overlay
                radioButton.click();
                logger.info(option + " radio button clicked successfully.");
            } catch (ElementClickInterceptedException e) {
                // If click is intercepted, try clicking via JavaScript
                logger.info("Click intercepted, trying JavaScript click...");
                scrollToElement(radioButton);
                logger.error(option + " radio button clicked using JavaScript.");
            } catch (TimeoutException e) {
                logger.error("Timeout: The '" + option + "' radio button is not clickable.");
                throw e; // Rethrow the exception after logging
            }
        }
    }

    public boolean isNoRadioButtonDisabled() {
        logger.info("No Radio Button is disabled");
        return  driver.findElement(By.xpath(noRadioButtonXpath)).getAttribute("class").contains("disabled");
    }

    public void clickButton(String buttonName) {
        logger.info("click Button method:"+buttonName);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(buttonXpath, buttonName))));
        action = new Actions(driver);
        // Scroll element into view
        scrollToElement(element);
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
            logger.info("Expected: " + expectedMessage + " | Found: " + message);
            return message;
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Message element not found for: " + expectedMessage);
        }

    }
}
