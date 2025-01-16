package pages;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

public class TextBoxPage extends BaseTest {

    public TextBoxPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getFieldByName(Fields fieldName){
        return driver.findElement(By.xpath(String.format("//*[@id='userForm']/div/div[2]/*[@id='%s']",fieldName.getLocator())));
    }

    public static String submitButtonXpath = "//*[@id='userForm']/*/*/button[text()='Submit']";
//    public static String inputSelectionXpath = "//*[@class='menu-list']/li/*[text()='%s']";
    public static String outputSectionXpath = "//div[contains(@class,'border')]/*[@id='%s']";
    public static Map<String,String> formFields;
    public Actions actions;
    public final WebDriverWait wait;

    public void fillTextBoxForm(String field, String value) {
            getFieldByName(Fields.valueOf(field)).sendKeys(value);
            System.out.println("Entered '" + value + "' in field: " + field);
    }

    public void clickSubmitButton() {
        actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(submitButtonXpath));
        actions.moveToElement(element).click().perform();
    }

    // Method to check if the output section is displayed
    public boolean isOutputSectionDisplayed() {
        try {
            // Wait for the output section to appear
            WebElement outputSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("output")));
            // Check if the content div with the "border" class exists
            return !outputSection.findElements(By.className("border")).isEmpty();
        } catch (TimeoutException e) {
            return false; // Output section not displayed
        }

//        return !driver.findElements(By.xpath(outputSectionXpath)).isEmpty() && driver.findElement(By.xpath(outputSectionXpath)).isDisplayed();
    }

    // Method to get the output section text
    public String getOutputText() {
        if (isOutputSectionDisplayed()) {
            return driver.findElement(By.xpath(outputSectionXpath)).getText();
        }
        return "";
    }

    public String getOutputText(String fieldId){
        WebElement field = driver.findElement(By.xpath(String.format(outputSectionXpath,fieldId)));
        return field.getText();
    }

}
