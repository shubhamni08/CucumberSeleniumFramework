package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import utility.DriverManager;

public class BaseTest {
    protected WebDriver driver;
    public static String menulistXpath = "//*[@class='menu-list']/li/*[text()='%s']";

    public static String homePageCardXpath = "//*[@class='card-body']/*[text()='%s']";

    public BaseTest() {
        this.driver = DriverManager.getDriver(null); // Get the existing driver instance
    }

    /**
     * Handle unexpected alerts gracefully to avoid UnhandledAlertException.
     */
    protected void dismissUnexpectedAlert() {
        try {
            driver.switchTo().alert().dismiss();
        } catch (Exception ignored) {
            // No alert present, continue execution.
        }
    }

}
