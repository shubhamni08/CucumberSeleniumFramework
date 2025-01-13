package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverManager() {}

    // Singleton method to get the driver instance
    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            if (browser == null || browser.isEmpty()) {
                browser = "chrome"; // Default to Chrome if no browser is specified
            }
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
//                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
//                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver());
//                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }

        }
        driver.get().manage().window().maximize();
        return driver.get();
    }

    // Method to close the driver
    public static void quitDriver() {
        if (driver != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
