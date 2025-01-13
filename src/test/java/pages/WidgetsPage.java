package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WidgetsPage extends BasePage {
    public WidgetsPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public static String accordianSectionXpath = "//*[@class='card']//*[text()='%s']";

    public static String accordianSectionContentXpath = "//div[@class='accordion']//div[@class='card-header' and text()='%s']/following-sibling::div[contains(@class, 'collapse')]//div[contains(@class, 'card-body')]/p";

    public static String progressBarButtonXpath = "//*[text()='%s']";

    public static String progressBarXpath = "//*[@role='progressbar']";

    public static String tabNameXpath = "//*[@role='tab' and text()='%s']";

    public static String tabContentXpath = "//*[@id='demo-tabpane-%s']"; //*[@role='tabpanel' and contains(@id,'%s')]";

    public final WebDriverWait wait;

    public void expandAccordionSection(String sectionName) {
        String sectionXpath = String.format(accordianSectionXpath, sectionName);
        try {
            WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sectionXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
            section.click();
        } catch (TimeoutException e) {
            System.out.println("TimeOutException: "+e);
        }
    }

    public boolean isAccordionContentDisplayed(String sectionName) {
        String contentXpath = String.format(accordianSectionContentXpath, sectionName);
        try {
            WebElement cardContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(contentXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardContent);
            return cardContent.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void moveSliderToValue(String sliderValue){
        WebElement slider = driver.findElement(By.xpath("//*[@type='range']"));

        // Use JavaScript to set the value
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + sliderValue + "'; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", slider);

        // Wait for the slider to reflect the change in the tooltip or input field
        WebElement tooltip = driver.findElement(By.xpath("//div[contains(@class, 'range-slider__tooltip__label')]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.textToBePresentInElement(tooltip, sliderValue));
        wait.until(ExpectedConditions.textToBePresentInElement(
                driver.findElement(By.xpath("//div[contains(@class, 'range-slider__tooltip__label')]")),
                sliderValue
        ));

    }

    public String getSliderValue() {
        return driver.findElement(By.xpath("//*[@type='range']")).getAttribute("value");
    }

    public void clickProgressBarButton(String buttonName) throws InterruptedException{
        WebElement button = driver.findElement(By.xpath(String.format(progressBarButtonXpath, buttonName)));
        if(buttonName.equals("Stop")){
            Thread.sleep(5000);
        }
        button.click();
    }

    public boolean isProgressBarComplete(String percentage) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*[@role='progressbar']"), percentage));
    }

    public String getProgressBarValue() {
        WebElement progressBar = driver.findElement(By.xpath(progressBarXpath));
        return progressBar.getText();
    }

    public void clickOnTab(String tabName) {
        String tabXpath = String.format(tabNameXpath, tabName);
        try {
            WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tabXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tab);
            tab.click();
        } catch (TimeoutException e) {
            System.out.println("TimeOutException: "+e);
        }

    }

    public boolean isTabContentDisplayed(String tabName) {
        String contentXpath = String.format(tabContentXpath, tabName);
        try {
            WebElement tabContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(contentXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabContent);
            return tabContent.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isMoreButtonDisabled(String tabName) {
        System.out.println( "isMoreButtonDisabled");
//        String moreTabXpath = String.format(tabContentXpath, tabName.toLowerCase());
        WebElement moreTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='demo-tab-more' and text()='More']")));
//        WebElement moreButton = driver.findElement(By.xpath("//*[@id='demo-tab-more']"));
        System.out.println(moreTab.getAttribute("aria-disabled").equals("true"));
        return moreTab.getAttribute("aria-disabled").equals("true");
    }
}
