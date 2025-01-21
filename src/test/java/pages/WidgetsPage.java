package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import utility.LoggerFactory;
import utility.Waits;

public class WidgetsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(WidgetsPage.class);
    private Waits waits;

    public WidgetsPage() {
        super();
        this.waits = new Waits();
    }

    public static String accordianSectionXpath = "//*[@class='card']//*[text()='%s']";

    public static String accordianSectionContentXpath = "//div[@class='accordion']//div[@class='card-header' and text()='%s']/following-sibling::div[contains(@class, 'collapse')]//div[contains(@class, 'card-body')]/p";

    public static String progressBarButtonXpath = "//*[text()='%s']";

    public static String progressBarXpath = "//*[@role='progressbar']";

    public static String tabNameXpath = "//*[@role='tab' and text()='%s']";

    public static String tabContentXpath = "//*[@id='demo-tabpane-%s']";

    public static String moreButtonXpath = "//*[@id='demo-tab-more' and text()='%s']";

    public static String rangeXpath = "//*[@type='range']";

    public void expandAccordionSection(String sectionName) {
        String sectionXpath = String.format(accordianSectionXpath, sectionName);
        try {
            WebElement section = waits.waitForVisiblityOfElement(By.xpath(sectionXpath));
            scrollToElement(section);
            section.click();
        } catch (TimeoutException e) {
            logger.error("TimeOutException: "+e);
        }
    }

    public boolean isAccordionContentDisplayed(String sectionName) {
        String contentXpath = String.format(accordianSectionContentXpath, sectionName);
        try {
            WebElement cardContent = waits.waitForVisiblityOfElement(By.xpath(contentXpath));
            scrollToElement(cardContent);
            return cardContent.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void moveSliderToValue(String sliderValue){
        try {
            // Locate the slider element
            WebElement slider = driver.findElement(By.xpath(rangeXpath));
            logger.info("Slider found: " + slider);

            // Log slider attributes
            logger.info("Initial slider value: " + slider.getAttribute("value"));
            logger.info("Min value: " + slider.getAttribute("min"));
            logger.info("Max value: " + slider.getAttribute("max"));

            // Using JS Executor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value='" + sliderValue + "';" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", slider);

            logger.info("Attempted to set slider value to: " + sliderValue);

            // Wait for the slider to reflect the new value
            boolean isValueSet = waits.waitForAttributeToBe(slider,"value",sliderValue);

            // Wait for 2 seconds to ensure the slider value is updated
            Thread.sleep(1000);
            String currentValue = slider.getAttribute("value");

            if (currentValue.equals(sliderValue)) {
                logger.info("Slider value successfully updated to: " + sliderValue);
            } else {
                logger.info("Slider value update failed. Current value: " + currentValue);
            }
        } catch (TimeoutException e) {
            logger.error("Timeout while waiting for slider value to update to: " + sliderValue);
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted.");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("An error occurred while moving the slider to value: " + sliderValue);
            e.printStackTrace();
        }
    }

    public String getSliderValue() {
        WebElement slider = driver.findElement(By.xpath(rangeXpath));
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
        By precentageLocator = By.xpath(progressBarXpath);
        return waits.waitForTextOfElementPresent(precentageLocator,percentage);
    }

    public String getProgressBarValue() {
        WebElement progressBar = driver.findElement(By.xpath(progressBarXpath));
        return progressBar.getText();
    }

    public void clickOnTab(String tabName) {
        String tabXpath = String.format(tabNameXpath, tabName);
        try {
            WebElement tab = waits.waitForVisiblityOfElement(By.xpath(tabXpath));
            scrollToElement(tab);
            tab.click();
        } catch (TimeoutException e) {
            logger.error("TimeOutException: "+e);
        }

    }

    public boolean isTabContentDisplayed(String tabName) {
        String contentXpath = String.format(tabContentXpath, tabName);
        try {
            WebElement tabContent = waits.waitForVisiblityOfElement(By.xpath(contentXpath));
            scrollToElement(tabContent);
            return tabContent.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isMoreButtonDisabled(String tabName) {
        logger.info( "isMoreButtonDisabled");
        By moreButtonLocator = By.xpath(String.format(moreButtonXpath,tabName));
        WebElement moreTab = waits.waitForVisiblityOfElement(moreButtonLocator);
        logger.info(moreTab.getAttribute("aria-disabled").equals("true"));
        return moreTab.getAttribute("aria-disabled").equals("true");
    }
}
