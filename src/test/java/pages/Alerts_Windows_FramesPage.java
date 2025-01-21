package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Set;

public class Alerts_Windows_FramesPage extends BasePage {
    public static String modelDialogButtonXpath = "//*[@id='modalWrapper']/div/*[text()='%s']";
    public static String closeButtonXpath = "//button[text()='%s']";
    public static String browserWindowButtonXpath = "//*[text()='Browser Windows']/following-sibling::div/button[text()='%s']";
    public static String alertButtonXpath = "//*[@id='%s']";
    private static final Logger logger = LoggerFactory.getLogger(Alerts_Windows_FramesPage.class);
    private Waits waits;

    public Alerts_Windows_FramesPage() {
        super();
        this.waits = new Waits();
    }

    public void openModal(String modal){
        By modelLocator = By.xpath(String.format(modelDialogButtonXpath,modal));
        WebElement element = waits.waitForElementToBeClickable(modelLocator);
        element.click();
    }

    public String getModalContent() {
        return driver.findElement(By.cssSelector(".modal-body")).getText();
    }

    public void closeModal(String buttonLabel) {
        super.clickButton(String.format(closeButtonXpath,buttonLabel));
    }

    public boolean isModalVisible() {
        logger.info(driver.findElements(By.className("modal-dialog")).isEmpty());
        return !driver.findElements(By.className("modal-dialog")).isEmpty();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public boolean switchBetweenAllWindowsAndVerify() {
        Set<String> allWindowHandles = driver.getWindowHandles();
        boolean verified = true;
        for (String handle : allWindowHandles) {
            driver.switchTo().window(handle);
            verified = verified && driver.getPageSource().contains("This is a sample page");
        }
        return verified;
    }

    public void verifyNewWindow(String windowName){
        // Store the parent window handle
        String parentHandle = driver.getWindowHandle();
        logger.info("Parent window handle: " + parentHandle);

        // Locate and click the button to open the new window
        WebElement newWindowButton = driver.findElement(By.xpath(String.format(browserWindowButtonXpath, windowName)));
        newWindowButton.click();

        // Wait for the new window to appear
        waits.waitForCondition(driver->driver.getWindowHandles().size()>1);

        // Get all window handles and switch to the new window
        Set<String> allHandles = driver.getWindowHandles();

        for(String handle :allHandles){
            logger.info(handle);
            if(!handle.equals(parentHandle)){
                driver.switchTo().window(handle);
                logger.info("Switched to new window with handle: " + handle);
                String newWindowTitle = driver.getTitle();
                logger.info("New window title: " + newWindowTitle);
                Assert.assertNotEquals(parentHandle, handle, "Failed to switch to the new window");

                // Perform any actions or assertions in the new window
                Assert.assertTrue(newWindowTitle.contains("https://demoqa.com/sample"), "Content validation failed");

                // Close the new window and switch back to the parent
                driver.close();
                break;
            }

        }
        // Switch back to the parent window
        driver.switchTo().window(parentHandle);
        logger.info("Switched back to parent window");
    }


    //alert page interaction methods
    public void clickAlertButton(String btn) {
        super.clickButton(String.format(alertButtonXpath,btn));
    }

    public String getAlertMessageSafely() {
        try {
            Alert alert = waits.waitForAlertPresence();
            String alertText = alert.getText();
            logger.info("Alert message: " + alertText);
            return alertText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void acceptAlertSafely() {
        try {
            Alert alert = waits.waitForAlertPresence();
            alert.accept();
            //TODO Chech that alert not present anymore
        } catch (NoAlertPresentException e) {
            logger.error("No alert present to accept.");
        }
    }

    public void dismissAlertSafely() {
        try {
            Alert alert = waits.waitForAlertPresence();
            alert.dismiss();
            //TODO Chech that alert not present anymore
        } catch (NoAlertPresentException e) {
            logger.error("No alert present to dismiss.");
        }
    }

    public void enterTextInPromptAlertSafely(String text) {
        try {
            Alert alert = waits.waitForAlertPresence();
            logger.info(text);
            alert.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getConfirmationResult() {
        By confirmResultLocator = By.id("confirmResult");
        WebElement resultElement = waits.waitForVisiblityOfElement(confirmResultLocator);
        return resultElement.getText();
    }

    public String getPromptResult() {
        By promptResultLocator = By.id("promptResult");
        WebElement resultElement = waits.waitForVisiblityOfElement(promptResultLocator);
        return resultElement.getText();
    }

}
