package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Set;
import Base.BaseTest;

public class Alerts_Windows_FramesPage extends BasePage {
    public static String modelDialogButtonXpath = "//*[@id='modalWrapper']/div/*[text()='%s']";
    public static String closeButtonXpath = "//button[text()='%s']";
    public static String browserWindowButtonXpath = "//*[text()='Browser Windows']/following-sibling::div/button[text()='%s']";
    public static String alertButtonXpath = "//*[@id='%s']";
    private static final Logger logger = LoggerFactory.getLogger(Alerts_Windows_FramesPage.class);
    private final Waits waits;
    private BaseTest baseTest;

    public Alerts_Windows_FramesPage() {
        super();
        this.waits = new Waits(driver);
        baseTest = new BaseTest();
    }

    public void openModal(String modal){
        By modelLocator = By.xpath(String.format(modelDialogButtonXpath,modal));
        WebElement element = waits.waitForElementToBeClickable(modelLocator);
        element.click();
        logger.info("Opened modal: {}", modal);
    }

    public String getModalContent() {
        return driver.findElement(By.cssSelector(".modal-body")).getText();
    }

    public void closeModal(String buttonLabel) {
        super.clickButton(String.format(closeButtonXpath,buttonLabel));
        logger.info("Closed modal using button: {}", buttonLabel);
    }

    public boolean isModalVisible() {
        boolean isVisible = !driver.findElements(By.className("modal-dialog")).isEmpty();
        logger.info("Is modal visible: {}", isVisible);
        return isVisible;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public boolean switchBetweenAllWindowsAndVerify() {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            driver.switchTo().window(handle);
            if (!driver.getPageSource().contains("This is a sample page")) {
                return false;
            }
        }
        return true;
    }

    public void verifyNewWindow(String windowName){
        // Store the parent window handle
        String parentHandle = driver.getWindowHandle();
        logger.info("Parent window handle: {}", parentHandle);

        // Locate and click the button to open the new window
        WebElement newWindowButton = driver.findElement(By.xpath(String.format(browserWindowButtonXpath, windowName)));
        newWindowButton.click();

        // Wait for the new window to appear
        waits.waitForCondition(driver->driver.getWindowHandles().size()>1);

        // Get all window handles and switch to the new window
        Set<String> allHandles = driver.getWindowHandles();

        switchToNewWindow(parentHandle);
        String newWindowTitle = driver.getTitle();
        logger.info("New window title: {}", newWindowTitle);
        Assert.assertTrue(newWindowTitle.contains("https://demoqa.com/sample"), "New window content validation failed");

        driver.close(); // Close new window
        driver.switchTo().window(parentHandle);
        logger.info("Switched back to parent window.");

    }

    /** Switches to a newly opened window */
    private void switchToNewWindow(String parentHandle) {
        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!handle.equals(parentHandle)) {
                driver.switchTo().window(handle);
                logger.info("Switched to new window with handle: {}", handle);
                return;
            }
        }
        throw new IllegalStateException("No new window found to switch to!");
    }

    //alert page interaction methods
    public void clickAlertButton(String btn) {
        baseTest.dismissUnexpectedAlert();
        clickButton(String.format(alertButtonXpath,btn));
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

    /** Handles alerts safely (accept, dismiss, send keys) */
    private Alert getAlertSafely() {
        try {
            return waits.waitForAlertPresence();
        } catch (NoAlertPresentException e) {
            logger.error("No alert present.");
            return null;
        }
    }

    public void acceptAlertSafely() {
        Alert alert = getAlertSafely();
        if (alert != null) alert.accept();
    }

    public void dismissAlertSafely() {
        try {
            Alert alert = waits.waitForAlertPresence();
            alert.dismiss();
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
        return getResultText("confirmResult");
    }

    public String getPromptResult() {
        return getResultText("promptResult");
    }

    /** Gets confirmation result from UI */
    private String getResultText(String resultId) {
        By resultLocator = By.id(resultId);
        WebElement resultElement = waits.waitForVisiblityOfElement(resultLocator);
        return resultElement.getText();
    }

}
