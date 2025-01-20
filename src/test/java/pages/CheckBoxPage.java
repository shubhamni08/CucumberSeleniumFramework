package pages;

import Base.BasePage;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CheckBoxPage extends BasePage {

    public CheckBoxPage() {
        super();
    }

    public static String expandAllXpath = "//*[@title='Expand all']/*";

    public static String collapseAllXpath = "//*[@title='Collapse all']/*";

    public static String selectAllCheckBoxXpath = "//label[span[@class='rct-checkbox'] and span[contains(text(),'Home')]]/span[@class='rct-checkbox' or contains(text(),'Home')]";

    public static String selectCheckBoxXpath = "//span[@class='rct-title' and text()='%s']";

    public static String resultSelectedCheckBoxXpath = "//span[@class='text-success']";

    private static final Logger logger = LoggerFactory.getLogger(CheckBoxPage.class);

    public void clickExpandAll() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expandAllXpath)));
        element.click();
    }

    public void clickCollapseAll(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(collapseAllXpath)));
        element.click();
    }

    public void selectAllCheckBoxes() throws InterruptedException{
        driver.findElement(By.xpath(selectAllCheckBoxXpath)).click();
        Thread.sleep(2000);
    }

    public void selectCheckBox(String checkboxName) throws InterruptedException{
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(selectCheckBoxXpath, checkboxName))));
        logger.info("WebElement - "+element);
        scrollToElement(element);
        element.click();
        Thread.sleep(2000);
    }

    public void selectMultipleCheckBoxes(DataTable dataTable ){
        List<String> checkBoxNames = dataTable.asList();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        for (String checkBoxName : checkBoxNames) {
            logger.info("Selecting checkbox: " + checkBoxName);
            WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + checkBoxName + "']/preceding-sibling::span[@class='rct-checkbox']")));
            scrollToElement(checkbox);
            checkbox.click();
        }
    }

    public String successMessageActualResult(String expectedResult){
        List<WebElement> successElements = driver.findElements(By.xpath(resultSelectedCheckBoxXpath));
        return successElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.joining(" "));
    }
}
