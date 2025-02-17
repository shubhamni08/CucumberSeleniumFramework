package pages;

import Base.BasePage;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utility.*;
import java.util.List;
import java.util.stream.Collectors;

public class CheckBoxPage extends BasePage {

    public CheckBoxPage() {
        super();
        this.waits = new Waits(driver);
    }

    public static String expandAllXpath = "//*[@title='Expand all']/*";
    public static String collapseAllXpath = "//*[@title='Collapse all']/*";
    public static String selectCheckBoxXpath = "//span[@class='rct-title' and text()='%s']";
    public static String resultSelectedCheckBoxXpath = "//span[@class='text-success']";
    private static final Logger logger = LoggerFactory.getLogger(CheckBoxPage.class);
    private final Waits waits;

    public void clickExpandAll() {
        WebElement element = driver.findElement(By.xpath(expandAllXpath));
        element = waits.waitForElementVisibility(element);
        element.click();
    }

    public void clickCollapseAll(){
        WebElement element = driver.findElement(By.xpath(collapseAllXpath));
        element = waits.waitForElementVisibility(element);
        element.click();
    }

    public void selectCheckBox(String checkboxName) throws InterruptedException{
        WebElement element = driver.findElement(By.xpath(String.format(selectCheckBoxXpath, checkboxName)));
        element = waits.waitForElementVisibility(element);
        logger.info("WebElement - "+element);
        scrollToElement(element);
        element.click();
        Thread.sleep(2000);
    }

    public void selectMultipleCheckBoxes(DataTable dataTable ){
        List<String> checkBoxNames = dataTable.asList();
        for (String checkBoxName : checkBoxNames) {
            logger.info("Selecting checkbox: " + checkBoxName);
            WebElement checkbox = driver.findElement(By.xpath("//span[text()='" + checkBoxName + "']/preceding-sibling::span[@class='rct-checkbox']"));
            checkbox = waits.waitForElementVisibility(checkbox);
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
