package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WidgetsPage extends BasePage {
    public WidgetsPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static String accordianSectionXpath = "//*[@class='card']//*[text()='%s']";

    public static String accordianSectionContentXpath = "//div[@class='accordion']//div[@class='card-header' and text()='%s']/following-sibling::div[contains(@class, 'collapse')]//div[contains(@class, 'card-body')]/p";

    public static String progressBarButtonXpath = "//*[text()='%s']";

    public static String progressBarXpath = "//*[@role='progressbar']";

    public static String tabNameXpath = "//*[@role='tab' and text()='%s']";

    public static String tabContentXpath = "//*[@id='demo-tabpane-%s']";

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
        try {
            // Locate the slider element
            WebElement slider = driver.findElement(By.xpath("//*[@type='range']"));
            System.out.println("Slider found: " + slider);

            // Log current slider attributes
            System.out.println("Initial slider value: " + slider.getAttribute("value"));
            System.out.println("Min value: " + slider.getAttribute("min"));
            System.out.println("Max value: " + slider.getAttribute("max"));

            // Using JS Executor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value='" + sliderValue + "';" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", slider);

            System.out.println("Attempted to set slider value to: " + sliderValue);

            // Wait for the slider to reflect the new value
            boolean isValueSet = wait.until(ExpectedConditions.attributeToBe(slider, "value", sliderValue));

            // Wait for 2 seconds to ensure the slider value is updated
            Thread.sleep(1000);
            String currentValue = slider.getAttribute("value");

            if (currentValue.equals(sliderValue)) {
                System.out.println("Slider value successfully updated to: " + sliderValue);
            } else {
                System.out.println("Slider value update failed. Current value: " + currentValue);
            }
        } catch (TimeoutException e) {
            System.err.println("Timeout while waiting for slider value to update to: " + sliderValue);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Thread sleep interrupted.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred while moving the slider to value: " + sliderValue);
            e.printStackTrace();
        }
    }

    public String getSliderValue() {
        WebElement slider = driver.findElement(By.xpath("//*[@type='range']"));
        return slider.getAttribute("value");
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
        WebElement moreTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='demo-tab-more' and text()='More']")));
        System.out.println(moreTab.getAttribute("aria-disabled").equals("true"));
        return moreTab.getAttribute("aria-disabled").equals("true");
    }
}
