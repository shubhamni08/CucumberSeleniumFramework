package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import utility.LoggerFactory;
import utility.Waits;

import java.util.Map;
import java.util.NoSuchElementException;

public class RadioButtonPage extends BasePage {

    public RadioButtonPage() {
        super();
        this.waits = new Waits(driver);
    }

    Actions action;
    public static String radioButtonXpath = "//*[@name='like']/following-sibling::label[text()='%s']";
    public static String noRadioButtonXpath = "//label[@for='noRadio']";
    public static String buttonXpath = "//*[@type='button' and text()='%s']";
//    private static final String SELECTED_MESSAGE_XPATH = "//span[@class='text-success']";
    private static final Logger logger = LoggerFactory.getLogger(RadioButtonPage.class);
    private final Waits waits;

    public void selectRadioButtonOption(String option){
        // Define the XPath for the radio button based on the common XPath format
        String optionXpath = String.format(radioButtonXpath, option);

        // Wait for the radio button to be visible first, then clickable
        WebElement radioButton = waits.waitForVisiblityOfElement(By.xpath(optionXpath));

        // Check if the radio button is disabled
        if (radioButton.getAttribute("disabled") != null) {
            logger.info("The '{}' radio button is disabled and cannot be selected.",option);
        } else {
            try {
                // Wait for the radio button to be clickable
                radioButton = waits.waitForElementToBeClickable(By.xpath(optionXpath));

                // Scroll the element into view if it's out of the viewport
                scrollToElement(radioButton);

                // Retry the click in case it was blocked by an overlay
                radioButton.click();
                logger.info(option ,"{} radio button clicked successfully.");
            } catch (ElementClickInterceptedException e) {
                // If click is intercepted, try clicking via JavaScript
                logger.info("Click intercepted, trying JavaScript click...");
                scrollToElement(radioButton);
                logger.error(option, "{} radio button clicked using JavaScript.");
            } catch (TimeoutException e) {
                logger.error("Timeout: The '{}' radio button is not clickable.",option);
                throw e; // Rethrow the exception after logging
            }
        }
    }

    public boolean isNoRadioButtonDisabled() {
        logger.info("No Radio Button is disabled");
        return  driver.findElement(By.xpath(noRadioButtonXpath)).getAttribute("class").contains("disabled");
    }

    public void clickButton(String buttonName) {
        logger.info("click Button method: {}",buttonName);
        String buttonLocator = String.format(buttonXpath, buttonName);
        WebElement element = waits.waitForElementToBeClickable(By.xpath(buttonLocator));
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
            WebElement element = waits.waitForVisiblityOfElement(By.xpath(xpath));
            String message = element.getText();
            logger.info("Expected: {} | message {}", expectedMessage,message);
            return message;
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Message element not found for: " + expectedMessage);
        }

    }
}
