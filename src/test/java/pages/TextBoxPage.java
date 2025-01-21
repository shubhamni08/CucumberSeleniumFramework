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

    private Waits waits;

    public TextBoxPage() {
        super();
        this.waits = new Waits();
    }

    public WebElement getFieldByName(Fields fieldName){
        return driver.findElement(By.xpath(String.format("//*[@id='userForm']/div/div[2]/*[@id='%s']",fieldName.getLocator())));
    }

    private static final Logger logger = LoggerFactory.getLogger(TextBoxPage.class);
    public static String submitButtonXpath = "//*[@id='userForm']/*/*/button[text()='Submit']";
//    public static String inputSelectionXpath = "//*[@class='menu-list']/li/*[text()='%s']";
    public static String outputSectionXpath = "//div[contains(@class,'border')]/*[@id='%s']";
    public static Map<String,String> formFields;
    public Actions actions;

    public void fillTextBoxForm(String field, String value) {
        getFieldByName(Fields.valueOf(field)).sendKeys(value);
        logger.info("Entered '" + value + "' in field: " + field);
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
            By outputLocator = By.id("output");
            WebElement outputSection =  waits.waitForElementToBePresence(outputLocator);

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
        WebElement field = driver.findElement(By.xpath(String.format(outputSectionXpath,fieldId)));
        return field.getText();
    }

}
