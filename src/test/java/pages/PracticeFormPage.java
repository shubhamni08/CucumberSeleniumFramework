package pages;

import Base.BasePage;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import utility.LoggerFactory;
import utility.Waits;

public class PracticeFormPage extends BasePage {

    public PracticeFormPage() {
        super();
        this.waits = new Waits(driver);
    }

    private static final String studentInputXpath = "//*[@id='%s']";
    private static final String genderXpath = "//input[@value='%s']";
    private static final String hobbiesXpath = "//*[text()='%s']";
    private static final String submitButtonXpath = "//*[text()='Submit']";
    private static final Logger logger = LoggerFactory.getLogger(PracticeFormPage.class);
    private final Waits waits;

    public void fillStudentRegistrationForm(String field, String value){
        //get locator of field
        Fields fieldEnum = Fields.getLocatorByFieldName(field);
        WebElement element;

        switch (fieldEnum) {
            case GENDER:
                selectRadioButton(value);
                break;

            case HOBBIES:
                selectMultipleCheckboxes(value);
                break;

            default:
                element = getFieldByName(fieldEnum);
                scrollToElement(element);
                element.sendKeys(value);
                logger.info("Entered '{}' in field: {}", value, field);
                break;
        }
    }


    // Select a radio button based on gender
    private void selectRadioButton(String gender) {
        String genderXpathFormatted = String.format(genderXpath, gender);
        WebElement radioButton = driver.findElement(By.xpath(genderXpathFormatted));
        scrollToElement(radioButton);

        try {
            // Attempt normal click first
            radioButton.click();
            logger.info("Selected gender: {}", gender);
        } catch (ElementClickInterceptedException e) {
            // Retry using JavaScript Click if normal click fails
            logger.warn("Click intercepted, using JS Click for: {}", gender);
            clickUsingJS(radioButton);
        } catch (StaleElementReferenceException e) {
            // Handle stale element by re-locating and clicking again
            logger.warn("Stale element detected, re-locating and retrying click for: {}", gender);
            radioButton = driver.findElement(By.xpath(genderXpathFormatted));
            clickUsingJS(radioButton);
        }
    }

    // Select multiple checkboxes for hobbies
    private void selectMultipleCheckboxes(String hobbies) {
        String[] hobbyList = hobbies.split(",\\s*"); // Split by comma and trim spaces
        for (String hobby : hobbyList) {
            String hobbyXpathFormatted = String.format(hobbiesXpath, hobby);
            WebElement checkbox = driver.findElement(By.xpath(hobbyXpathFormatted));
            clickUsingJS(checkbox);
            logger.info("Selected hobby: {}", hobby);
        }
    }

    public WebElement getFieldByName(Fields fieldName){
        return driver.findElement(By.xpath(String.format(studentInputXpath, fieldName.getLocator())));
    }

    public void submitForm(){
        WebElement submitButton = waits.waitForElementToBeClickable(By.xpath(submitButtonXpath));
        scrollToElement(submitButton);
        submitButton.click();
    }

    public void verifySubmission(){
        // Wait for confirmation dialog
        WebElement confirmationDialog = waits.waitForVisiblityOfElement(By.xpath("//*[@class='modal-content']"));
        // Verify dialog content
        boolean isDialogVisible = confirmationDialog.isDisplayed();
        if (!isDialogVisible) {
            throw new AssertionError("Confirmation dialog not displayed.");
        }

        // Optionally, validate content of the dialog
        WebElement confirmationTable = confirmationDialog.findElement(By.xpath(".//table"));
        if (confirmationTable == null) {
            throw new AssertionError("Confirmation table not found.");
        }

        logger.info("Form submitted successfully, confirmation details displayed.");
    }

}
