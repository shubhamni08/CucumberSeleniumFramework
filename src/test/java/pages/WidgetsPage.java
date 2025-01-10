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
    }

    public static String accordianSectionXpath = "//*[@class='card']//*[text()='%s']";

    public static String accordianSectionContentXpath = "//div[@class='accordion']//div[@class='card-header' and text()='%s']/following-sibling::div[contains(@class, 'collapse')]//div[contains(@class, 'card-body')]/p";

    public static String progressBarButtonXpath = "//*[text()='%s']";

    public static String progressBarXpath = "//*[@role='progressbar']";

    public static String tabNameXpath = "//*[@role='tab' and text()='%s']";

    public static String tabContentXpath = "//*[@id='demo-tabpane-%s']"; //*[@role='tabpanel' and contains(@id,'%s')]";

    public void expandAccordionSection(String sectionName) {
        //usage of explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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


//        Actions actions = new Actions(driver);
//
//        // Get the current value of the slider
//        int currentValue = Integer.parseInt(slider.getAttribute("value"));
//        int targetValue = Integer.parseInt(sliderValue);
//
//        // Ensure the target value is within the slider's range
//        int minValue = Integer.parseInt(slider.getAttribute("min"));
//        int maxValue = Integer.parseInt(slider.getAttribute("max"));
//
//        if (targetValue < minValue || targetValue > maxValue) {
//            throw new IllegalArgumentException("Target value is out of range!");
//        }
//
//        // Calculate the pixel offset based on the slider's range and width
//        int sliderWidth = slider.getSize().getWidth();
//        int range = maxValue - minValue;
//        int offsetPixels = (sliderWidth * (targetValue - currentValue)) / range;

        // Perform the drag-and-drop action
//        actions.dragAndDropBy(slider, offsetPixels, 0).perform();
//
//        // Wait for the slider value to update
//        wait.until(ExpectedConditions.attributeToBe(slider, "value", sliderValue));

//        int offset = Integer.parseInt(sliderValue) - Integer.parseInt(slider.getAttribute("value"));
//        actions.dragAndDropBy(slider, offset, 0).perform();


//        wait.until(ExpectedConditions.attributeToBe(slider, "value", sliderValue));


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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//*[@role='progressbar']"), percentage));
    }

    public String getProgressBarValue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement progressBar = driver.findElement(By.xpath(progressBarXpath));
        return progressBar.getText();
    }

    public void clickOnTab(String tabName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        String moreTabXpath = String.format(tabContentXpath, tabName.toLowerCase());
        WebElement moreTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='demo-tab-more' and text()='More']")));
//        WebElement moreButton = driver.findElement(By.xpath("//*[@id='demo-tab-more']"));
        System.out.println(moreTab.getAttribute("aria-disabled").equals("true"));
        return moreTab.getAttribute("aria-disabled").equals("true");
    }
}
