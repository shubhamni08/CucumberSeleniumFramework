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
    }

    public static String fullNameInputXpath = "//*[@id='userForm']/div/div[2]/*[@id='userName']";
    public static String emailInputXpath = "//*[@id='userForm']/div/div[2]/*[@id='userEmail']";
    public static String currentAddressInputXpath = "//*[@id='userForm']/div/div[2]/*[@id='currentAddress']";
    public static String permanentAddressInputXpath = "//*[@id='userForm']/div/div[2]/*[@id='permanentAddress']";
    public static String submitButtonXpath = "//*[@id='userForm']/*/*/button[text()='Submit']";
//    public static String inputSelectionXpath = "//*[@class='menu-list']/li/*[text()='%s']";
    public static String outputSectionXpath = "//div[contains(@class,'border')]/*[@id='%s']";
    public static Map<String,String> formFields;
    public Actions actions;

    public void fillTextBoxForm(DataTable dataTable) {
        formFields = dataTable.asMap(String.class, String.class);
        System.out.println("Raw Table Data:");
        dataTable.cells().forEach(row -> System.out.println(row));


        for(Map.Entry<String,String> entry : formFields.entrySet()){
            String field = entry.getKey().trim().toLowerCase();
            String value = entry.getValue().trim();

            // Debugging log
            System.out.println("Processing field: '" + field + "', value: '" + value + "'");
            if ("field".equals(field)) {
                System.out.println("Skipping header row...");
                continue;
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            switch (field) {
                case "full name":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fullNameInputXpath)))
                            .sendKeys(value);
                    break;
                case "email":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(emailInputXpath)))
                                    .sendKeys(value);
                    break;
                case "current address":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(currentAddressInputXpath)))
                            .sendKeys(value);
                    break;
                case "permanent address":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(permanentAddressInputXpath)))
                            .sendKeys(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        }
    }

    public void clickSubmitButton() {
        actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = driver.findElement(By.xpath(submitButtonXpath));
        actions.moveToElement(element).click().perform();
    }

    // Method to check if the output section is displayed
    public boolean isOutputSectionDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
