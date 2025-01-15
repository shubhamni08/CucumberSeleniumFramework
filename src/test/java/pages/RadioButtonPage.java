package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public static String successMessageXpath = "//span[@class='text-success']";

    public static String noRadioButtonXpath = "//label[@for='noRadio']";

    public static String buttonXpath = "//*[@type='button' and text()='%s']";

//    public static String messageIdXpath = "//*[@id='%s']";

    public final WebDriverWait wait;

    public void selectRadioButtonOption(String value){
        WebElement element = driver.findElement(By.xpath(String.format(radioButtonXpath, value)));
        element.click();
    }

    public boolean isNoRadioButtonDisabled() {
        System.out.println("No Radio Button is disabled");
        return  driver.findElement(By.xpath(noRadioButtonXpath)).getAttribute("class").contains("disabled");
    }

//    public String getSuccessMessage() {
//        String msg =  driver.findElement(By.xpath(successMessageXpath)).getText();
//        return "You have selected "+msg;
//    }

    public void clickButton(String buttonName) {
        System.out.println("click Button method:"+buttonName);
        WebElement element = driver.findElement(By.xpath(String.format(buttonXpath, buttonName)));

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

    }

    public String getClickMessage(String expectedMessage){
        String xpath = "";
        // Map button messages to corresponding XPaths
        if (expectedMessage.equals("You have done a double click")) {
            xpath = "//*[@id='doubleClickMessage']";
        } else if (expectedMessage.equals("You have done a right click")) {
            xpath = "//*[@id='rightClickMessage']";
        } else if (expectedMessage.equals("You have done a dynamic click")) {
            xpath = "//*[@id='dynamicClickMessage']";
        } else {
            throw new IllegalArgumentException("Invalid expected message: " + expectedMessage);
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (element.isDisplayed()) {
                String message = element.getText();
                System.out.println("Found message: " + message);
                return message;
            }
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Message element not found for: " + expectedMessage);
        }

        return null;

//        for (Map.Entry<String, String> entry : messagesMap.entrySet()) {
//            try {
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//                System.out.print("Key - "+entry.getKey());
//                System.out.println("Value - "+entry.getValue());
//                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(entry.getKey())));
//                if (element.isDisplayed()) {
//                    String message = element.getText();
//                    System.out.println("Found message: " + message);
//                    return message;
//                }
//            } catch (TimeoutException e) {
//                // Do nothing, continue to next message
//            }
//        }
//
//        throw new NoSuchElementException("No message found for any click action.");
    }
}
