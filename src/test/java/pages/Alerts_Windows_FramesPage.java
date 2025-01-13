package pages;

import Base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Set;

public class Alerts_Windows_FramesPage extends BaseTest {
    public static String modelDialogButtonXpath = "//*[@id='modalWrapper']/div/*[text()='%s']";
    public static String closeButtonXpath = "//button[text()='%s']";
    public static String browserWindowButtonXpath = "//*[text()='Browser Windows']/following-sibling::div/button[text()='%s']";
    public static String alertButtonXpath = "//*[@id='%s']";
    public final WebDriverWait wait;
    private Alert alert;


    public Alerts_Windows_FramesPage() {
        super();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }



    public void openModal(String modal){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(modelDialogButtonXpath,modal))));
        element.click();
    }

    public String getModalContent() {
        return driver.findElement(By.cssSelector(".modal-body")).getText();
    }

    public void closeModal(String buttonLabel) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(closeButtonXpath,buttonLabel))));
        ele.click();
    }

    public boolean isModalVisible() {
        System.out.print(driver.findElements(By.className("modal-dialog")).isEmpty());
        return !driver.findElements(By.className("modal-dialog")).isEmpty();
    }

    public String getCurrentURL() {

        return driver.getCurrentUrl();
    }

    public void triggerNonExistentAlert() {
        driver.findElement(By.id("nonExistentAlertButton")).click();
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

    public void verifyNewTab(String tab){
        WebElement newTabElement  = driver.findElement(By.xpath(String.format(browserWindowButtonXpath,tab)));
        newTabElement.click();
    }

    public void verifyNewWindow(String windowName){
        // Store the parent window handle
        String parentHandle = driver.getWindowHandle();
        System.out.println("Parent window handle: " + parentHandle);

        // Locate and click the button to open the new window
        WebElement newWindowButton = driver.findElement(By.xpath(String.format(browserWindowButtonXpath, windowName)));
        newWindowButton.click();

        // Wait for the new window to appear
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver->driver.getWindowHandles().size()>1);

        // Get all window handles and switch to the new window
        Set<String> allHandles = driver.getWindowHandles();

        for(String handle :allHandles){
            System.out.println(handle);
            if(!handle.equals(parentHandle)){
                driver.switchTo().window(handle);
                System.out.println("Switched to new window with handle: " + handle);
                String newWindowTitle = driver.getTitle();
                System.out.println("New window title: " + newWindowTitle);
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
        System.out.println("Switched back to parent window");
    }


    //alert page interaction methods
    public void clickAlertButton(String btn) {
        try {
            WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(String.format(alertButtonXpath, btn))));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", alertButton);
            alertButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAlertMessageSafely() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert message: " + alertText);
            return alertText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void acceptAlertSafely() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to accept.");
        }
    }

    public void dismissAlertSafely() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to dismiss.");
        }
    }

    public void enterTextInPromptAlertSafely(String text) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println(text);
            alert.sendKeys(text);
//            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getConfirmationResult() {
        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmResult")));
        return resultElement.getText();
    }

    public String getPromptResult() {
        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("promptResult")));
        return resultElement.getText();
    }

}
