package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Map;

public class TextBoxPage extends BasePage {

    private final Waits waits;

    public TextBoxPage() {
        super();
        this.waits = new Waits(driver);
    }

    private static final Logger logger = LoggerFactory.getLogger(TextBoxPage.class);
    public static String submitButtonXpath = "//*[@id='userForm']/*/*/button[text()='Submit']";
    public static String userInputXpath = "//*[@id='userForm']/div/div[2]/*[@id='%s']";
    public static String outputSectionXpath = "//div[contains(@class,'border')]/*[@id='%s']";
    public static Map<String,String> formFields;
    public Actions actions;

    public void fillTextBoxForm(String field, String value) {
        Fields fieldEnum = Fields.getLocatorByFieldName(field);
        WebElement ele = getFieldByName(Fields.getLocatorByFieldName(field));
        ele.sendKeys(value);
        logger.info("Entered '{}' in field: {}",value,field);
    }

    public WebElement getFieldByName(Fields fieldName){
        return driver.findElement(By.xpath(String.format(userInputXpath,fieldName.getLocator())));
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
            WebElement outputSection =  waits.waitForElementToBePresence(By.id("output"));

            // Check if the content div with the "border" class exists
            return !outputSection.findElements(By.className("border")).isEmpty();
        } catch (TimeoutException e) {
            return false; // Output section not displayed
        }
}

    // Method to get the output section text
    public String getOutputText() {
        if (isOutputSectionDisplayed()) {
            return driver.findElement(By.xpath(outputSectionXpath)).getText();
        }
        return "";
    }

    public String getOutputText(String fieldId){
        By locator = By.xpath(String.format(outputSectionXpath,fieldId));
        waits.waitForVisiblityOfElement(locator);
        WebElement field = driver.findElement(locator);
        scrollToElement(field);
        return field.getText();
    }

}
