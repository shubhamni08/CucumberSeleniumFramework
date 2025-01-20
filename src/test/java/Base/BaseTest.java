package Base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.DriverManager;
import java.time.Duration;


public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
//    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(60);
//    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

    public BaseTest() {
        this.driver = DriverManager.getDriver(null);
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    protected void dismissUnexpectedAlert() {
        try {
            driver.switchTo().alert().dismiss();
        } catch (Exception ignored) {
            // No alert present, continue execution.
        }
    }

    private long laterBy(long durationInMillis) {
        return System.currentTimeMillis() + durationInMillis;
    }

    private boolean isNowBefore(long endInMillis) {
        return System.currentTimeMillis() < endInMillis;
    }
}
