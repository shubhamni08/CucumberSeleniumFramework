package pages;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class WebTablesPage extends BaseTest {
    public WebTablesPage(){
        super();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private static final String webTableInputXpath = "//form[@id='userForm']/div/*/*[@id='%s']";
    private static final String submitButtonXpath = "//button[@id='submit' and contains(text(), 'Submit')]";
    private static final String addButtonXpath = "//*[text()='Add']";
    private static final String editButtonXpath = "//div[@role='row' and .//div[text()='%s']]/*/div[@class='action-buttons']/span[@title='Edit']";
    private static final String deleteButtonXpath = "//div[@role='row' and .//div[text()='%s']]/*/div[@class='action-buttons']/span[@title='Delete']";
    private static final String entryRowXpath = "//div[@role='row' and .//div[text()='%s']]";
    private final WebDriverWait wait;
    private Actions actions;

    public void clickAddButton(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(addButtonXpath))).click();
    }

    public void fillTextBoxForm(DataTable dataTable) {
        Map<String, String> formFields = dataTable.asMap(String.class, String.class);
        System.out.println("Raw Table Data:");
        dataTable.cells().forEach(System.out::println);

        formFields.forEach((field,value)->{
            field = field.trim();
            value = value.trim();

            // Debugging log
            System.out.println("Processing field: '" + field + "', value: '" + value + "'");

            if ("field".equals(field)) {
                System.out.println("Skipping header row...");
                return;
            }

            try {
                By locator = By.xpath(String.format(webTableInputXpath, field));
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.clear();
                element.sendKeys(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown field: " + field, e);
            }

        });

    }

    public void submitForm() {
        try {
            WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(submitButtonXpath)));
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            // Scroll the submit button into view to ensure it's interactable
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            submitButton.click();
        } catch (TimeoutException e) {
            System.out.println("Submit button is not clickable after 10 seconds.");
            throw e;
        }
    }

    public boolean isEntryPresent(String identifier) {
        try {
            By entryRow = By.xpath(String.format(entryRowXpath, identifier));
            // Wait for the element to be present and visible in the DOM
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(entryRow));
            wait.until(ExpectedConditions.visibilityOf(element));  // Wait for the element to be visible
            // Scroll into view
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void editEntry(String identifier, DataTable updatedData) {
        System.out.println("Editing entry for identifier: " + identifier);
            try {
                // Locate and click the edit button
                WebElement editButton = locateAndClickEditButton(identifier);

                // Re-fill the form with updated data
                fillTextBoxForm(updatedData);

            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException encountered.");
            } catch (ElementClickInterceptedException e) {
                System.out.println("ElementClickInterceptedException encountered. Retrying with JavaScript click...");

                // Fallback to JavaScript click
                By editButtonLocator = By.xpath(String.format(editButtonXpath, identifier));
                WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(editButtonLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);

                // Re-fill the form with updated data
                fillTextBoxForm(updatedData);
            }
    }

    private WebElement locateAndClickEditButton(String identifier) {
        By editButtonLocator = By.xpath(String.format(editButtonXpath, identifier));
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(editButtonLocator));

        // Scroll into view and click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        editButton.click();

        return editButton;
    }

    public void deleteEntry(String identifier) {
        By deleteButton = By.xpath(String.format(deleteButtonXpath, identifier));
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteBtn);
        deleteBtn.click();
    }

}
