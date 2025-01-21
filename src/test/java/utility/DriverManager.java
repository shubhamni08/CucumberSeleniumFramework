package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String DEFAULT_BROWSER = "chrome";
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    // Private constructor to prevent instantiation
    private DriverManager() {}

    // Singleton method to get the driver instance
    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            synchronized (DriverManager.class) { // Ensure thread safety
                if (driver.get() == null) {
                    if (browser == null || browser.isEmpty()) {
                        browser = DEFAULT_BROWSER;
                    }
                    driver.set(createDriver(browser.toLowerCase()));
                    driver.get().manage().window().maximize();
                }
            }
        }
        return driver.get();
    }

    private static WebDriver createDriver(String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    // Method to close the driver
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("WebDriver session quit successfully.");
        }
    }
}
