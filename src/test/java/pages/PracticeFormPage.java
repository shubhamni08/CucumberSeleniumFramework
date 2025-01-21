package pages;

import Base.BasePage;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Map;

public class PracticeFormPage extends BasePage {

    public PracticeFormPage() {
        super();
        this.waits = new Waits(driver);
    }
    private static final String firstNameXpath = "//*[@id='firstName']";
    private static final String lastNameXpath = "//*[@id='lastName']";
    private static final String emailXpath = "//*[@id='userEmail']";
//    private static final String genderXpath = "//*[@id='genterWrapper']//*[@name='gender' and @value='Male']";
    private static final String mobileNoXpath = "//*[@id='userNumber']";
    private static final String currentAddressXpath = "//*[@id='currentAddress']";
    private static final String submitButtonXpath = "//*[text()='Submit']";
    private static final Logger logger = LoggerFactory.getLogger(PracticeFormPage.class);
    private final Waits waits;

    private WebElement getElementByXpath(String xpath) {
        WebElement element = waits.waitForVisiblityOfElement(By.xpath(xpath));
        // Scroll the element into view using JavaScript
        scrollToElement(element);
        return element;
    }


    public void fillPracticeForm(DataTable dataTable) {
        Map<String, String> formFields = dataTable.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : formFields.entrySet()) {
            String field = entry.getKey().trim().toLowerCase().replace(" ", "");
            String value = entry.getValue().trim();

            switch (field) {
                case "firstname":
                    getElementByXpath(firstNameXpath).sendKeys(value);
                    break;

                case "lastname":
                    getElementByXpath(lastNameXpath).sendKeys(value);
                    break;

                case "email":
                    getElementByXpath(emailXpath).sendKeys(value);
                    break;

                case "gender":
                    String genderXpath = String.format("//*[@id='genterWrapper']//input[@name='gender' and @value='%s']", value);
                    WebElement genderElement = waits.waitForElementToBePresence(By.xpath(genderXpath));
                    scrollToElement(genderElement);
                    // Click using JavaScript to avoid interception issues
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderElement);
                    break;

                case "mobilenumber":
                    getElementByXpath(mobileNoXpath).sendKeys(value);
                    break;

                case "dateofbirth":
                    WebElement dobElement = getElementByXpath("//*[@id='dateOfBirthInput']");
                    dobElement.click();
                    dobElement.sendKeys(value);
                    break;

                case "hobbies":
                    for (String hobby : value.split(",")) {
                        String hobbyXpath = String.format("//*[@id='hobbiesWrapper']//*[text()='%s']/preceding-sibling::input", hobby.trim());
                        WebElement hobbyElement = waits.waitForElementToBePresence(By.xpath(hobbyXpath));

                        // Scroll into view
                        scrollToElement(hobbyElement);

                        // Click using JavaScript
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hobbyElement);
                    }
                    break;

                case "address":
                    getElementByXpath(currentAddressXpath).sendKeys(value);
                    break;

                case "state":

                case "city":

                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        }
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
