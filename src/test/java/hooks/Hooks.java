package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.Logger;
import utility.ConfigReader;
import utility.DriverManager;
import utility.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        try{
            String browser = ConfigReader.getInstance().getProperty("browser");
            DriverManager.getDriver(browser);
            logger.info("Test setup completed with browser: " + browser);
        }catch(Exception e){
            logger.error("Error during test setup: ", e);
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            DriverManager.quitDriver();
            logger.info("Test teardown completed. Browser closed.");
        } catch (Exception e) {
            logger.error("Error during test teardown: ", e);
        }
    }
}
