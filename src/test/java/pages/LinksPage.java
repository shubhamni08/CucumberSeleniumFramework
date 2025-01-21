package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utility.LoggerFactory;
import utility.Waits;
import java.util.Set;

public class LinksPage extends BasePage {
    public LinksPage() {
        super();
        this.waits = new Waits();
    }

    public static String linksXpath = "//*[text()='%s']";
    public static String responseCodeXpath = "//*[@id='linkResponse']";
    private static final Logger logger = LoggerFactory.getLogger(LinksPage.class);
    private Waits waits;

    public String getCurrentURL(){
        // Store the parent window handle
        String parentWindow = driver.getWindowHandle();
        waits.waitForNumberOfWindow(2);

        //switch to the new window
        Set<String> allWindows  = driver.getWindowHandles();

        for(String window:allWindows ){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }

        // Assert the current URL
        return driver.getCurrentUrl();
    }

    public void clickLink(String linkName) {
        WebElement element = driver.findElement(By.xpath(String.format(linksXpath, linkName)));
        element.click();
    }

    public String getAPIResponseText() {
        WebElement element = waits.waitForVisiblityOfElement(By.xpath(responseCodeXpath));
        String response = element.getText();
        logger.info("getAPIResponseText method successful: "+response);
        return response;
    }
}
