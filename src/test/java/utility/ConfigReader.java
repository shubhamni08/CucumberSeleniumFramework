package utility;

import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance;
    private final Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public ConfigReader() {
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)){
            properties.load(fis);
            logger.info("Loaded config.properties successfully.");
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }
}
