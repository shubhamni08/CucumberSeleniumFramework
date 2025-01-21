package pages;

import Base.BasePage;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Map;

public class WebTablesPage extends BasePage {

    public WebTablesPage(){
        super();
        this.waits = new Waits(driver);
    }

    private static final String webTableInputXpath = "//form[@id='userForm']/div/*/*[@id='%s']";
    private static final String submitButtonXpath = "//button[@id='submit' and contains(text(), 'Submit')]";
    private static final String addButtonXpath = "//*[text()='Add']";
    private static final String editButtonXpath = "//div[@role='row' and .//div[text()='%s']]/*/div[@class='action-buttons']/span[@title='Edit']";
    private static final String deleteButtonXpath = "//div[@role='row' and .//div[text()='%s']]/*/div[@class='action-buttons']/span[@title='Delete']";
    private static final String entryRowXpath = "//div[@role='row' and .//div[text()='%s']]";
    private static final Logger logger = LoggerFactory.getLogger(WebTablesPage.class);
    private final Waits waits;

    public void clickAddButton(){
        clickButton(addButtonXpath);
    }

    public void fillTextBoxForm(DataTable dataTable) {
        Map<String, String> formFields = dataTable.asMap(String.class, String.class);
        logger.info("Raw Table Data:");
        dataTable.cells().forEach(System.out::println);

        formFields.forEach((field,value)->{
            field = field.trim();
            value = value.trim();

            // Debugging log
            logger.info("Processing field: '{}', value: '{}'",field,value);

            if ("field".equals(field)) {
                logger.info("Skipping header row...");
                return;
            }

            try {
                By locator = By.xpath(String.format(webTableInputXpath, field));
                WebElement element = waits.waitForVisiblityOfElement(locator);
                element.clear();
                element.sendKeys(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown field: " + field, e);
            }

        });

    }

    public void submitForm() {
        try {
            clickButton(submitButtonXpath);
        } catch (TimeoutException e) {
            logger.error("Submit button is not clickable after 10 seconds.");
            throw e;
        }
    }

    public boolean isEntryPresent(String identifier) {
        try {
            By entryRow = By.xpath(String.format(entryRowXpath, identifier));
            // Wait for the element to be present and visible in the DOM
            WebElement element = waits.waitForElementToBePresence(entryRow);
            waits.waitForElementVisibility(element);    // Wait for the element to be visible
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void editEntry(String identifier, DataTable updatedData) {
        System.out.println("Editing entry for identifier: " + identifier);
            try {
                // Locate and click the edit button
                WebElement editButton =locateAndWaitForEditButton(identifier);
                editButton.click();

                // Re-fill the form with updated data
                fillTextBoxForm(updatedData);

            } catch (StaleElementReferenceException e) {
                logger.error("StaleElementReferenceException encountered.",e);
            } catch (ElementClickInterceptedException e) {
                logger.error("ElementClickInterceptedException encountered. Retrying with JavaScript click...",e);

                // Fallback to JavaScript click
                By editButtonLocator = By.xpath(String.format(editButtonXpath, identifier));
                WebElement editButton = waits.waitForElementToBePresence(editButtonLocator);
                scrollToElement(editButton);
                editButton.click();

                // Re-fill the form with updated data
                fillTextBoxForm(updatedData);
            }
    }

    private void locateAndClickEditButton(String identifier) {
        clickButton(String.format(editButtonXpath, identifier));
    }

    public void deleteEntry(String identifier) {
        clickButton(String.format(deleteButtonXpath, identifier));
    }

    private WebElement locateAndWaitForEditButton(String identifier) {
        By editButtonLocator = By.xpath(String.format(editButtonXpath, identifier));
        return waits.waitForElementToBeClickable(editButtonLocator);
    }

}
