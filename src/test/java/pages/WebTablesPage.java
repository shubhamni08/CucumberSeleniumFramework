package pages;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class WebTablesPage extends BaseTest {
    public WebTablesPage(){
        super();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private static final String webTableInputXpath = "//form[@id='userForm']/div/*/*[@id='%s']";
    private static final String submitButtonXpath = "//button[@id='submit']";
    private static final String addButtonXpath = "//*[text()='Add']";
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
            field = field.trim().toLowerCase();
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
                element.sendKeys(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown field: " + field, e);
            }

        });


    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(submitButtonXpath))).click();
    }



}
