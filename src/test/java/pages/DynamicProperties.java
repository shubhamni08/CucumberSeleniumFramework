package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utility.LoggerFactory;
import utility.Waits;

public class DynamicProperties extends BasePage {

    public DynamicProperties(){
        super();
        this.waits = new Waits(driver);
    }

    private static final Logger logger = LoggerFactory.getLogger(DynamicProperties.class); // Logger initialization
    public static String dynamicButtonXpath = "//*[contains(text(),'%s')]";
    private final Waits waits;

    public boolean buttonEnabledVisible(String btn){
        try {
            WebElement button = getButton(btn);
            scrollToElement(button);

            if(btn.equals("Will enable 5 seconds")){
                waits.waitForElementToBeClickable(button);
                return button.isEnabled();
            }else if(btn.equals("Visible After 5 Seconds")){
                waits.waitForElementVisibility(button);
                return button.isDisplayed();
            }
        } catch (TimeoutException e) {
            logger.error("{} button did not become visible/enable after 5 seconds", btn);
            throw new AssertionError(btn + " button did not become visible or enabled after 5 seconds", e);
        }
        return false;
    }

    // Helper method to find the button element by name
    private WebElement getButton(String btn) {
        return driver.findElement(By.xpath(String.format(dynamicButtonXpath, btn)));
    }

    public void btnChangeColor(String buttonName){
        WebElement button = getButton(buttonName);
        scrollToElement(button);

        // Get the initial color of the button
        String initialColor = button.getCssValue("color");
        logger.info("Initial color of {}: {}", buttonName, initialColor);

        waits.waitForCondition(driver -> !button.getCssValue("color").equals(initialColor));

        String newColor = button.getCssValue("color");
        logger.info("New color of {}: {}", buttonName, newColor);

        Assert.assertNotEquals(initialColor, newColor, buttonName + " button color did not change as expected.");
    }

}
