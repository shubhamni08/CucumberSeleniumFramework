package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utility.ConfigReader;
import utility.DriverManager;

public class Hooks {

    @Before
    public void setUp() {
        // Load the browser from config.properties
        ConfigReader configReader = new ConfigReader();
        String browser = configReader.getProperty("browser");

        // Initialize the WebDriver
        DriverManager.getDriver(browser);
    }

    @After
    public void tearDown() {
        // Quit the WebDriver
        DriverManager.quitDriver();
    }
}
