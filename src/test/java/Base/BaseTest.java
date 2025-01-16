package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.DriverManager;

import java.time.Duration;

import static pages.UploadDownloadPage.wait;

public class BaseTest {
    protected WebDriver driver;
    private final Duration timeout;
    private final WebDriverWait wait;
    public static String menulistXpath = "//*[@class='menu-list']/li/*[text()='%s']";

    public static String homePageCardXpath = "//*[@class='card-body']/*[text()='%s']";

    public BaseTest() {
        this.driver = DriverManager.getDriver(null);
        this.timeout = Duration.ofMinutes(1);
        this.wait = new WebDriverWait(driver, timeout);
        // Get the existing driver instance
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

    public void clickButton(String locator) {
        waitUntil(() ->
        {
            WebElement button = driver.findElement(By.xpath(locator));
            runJS("arguments[0].scrollIntoView(true);", button);
            button.click();
        }
        );
    }

    protected void runJS(String js, WebElement element){
        ((JavascriptExecutor) driver).executeScript(js, element);
    }

    /** Wait until function that accept :
     * function will try to successfully run received code and repeat it
     * until it will succeed or until timeout.
     * @param duration - set general duration in milliseconds
     * @param polling - set polling in milliseconds
     * @param runnable - set code that will be executed
     */
    public void waitUntil(Duration duration, Duration polling, Runnable runnable) {
        long end = laterBy(duration.toMillis());

        while (true) {
            try {
                runnable.run();
                return;
            } catch (AssertionError | RuntimeException e) {
                if (!isNowBefore(end)) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(polling.toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Default wait until function that accept :
     * default version of basic waitUntil with preset of duration (set to default framework timout)
     * and polling (set to 200 milliseconds).
     * function will try to successfully run received code and repeat it
     * until it will succeed or until timeout
     * @param runnable - set code that will be executed
     */
    public void waitUntil(Runnable runnable) {
        waitUntil(timeout,
                Duration.ofMillis(500), runnable);
    }
    private long laterBy(long durationInMillis) {
        return System.currentTimeMillis() + durationInMillis;
    }

    private boolean isNowBefore(long endInMillis) {
        return System.currentTimeMillis() < endInMillis;
    }
}
