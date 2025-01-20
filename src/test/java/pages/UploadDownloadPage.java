package pages;

import Base.BasePage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import utility.LoggerFactory;


public class UploadDownloadPage extends BasePage {

    public UploadDownloadPage(){
        super();
    }

    public static String downloadXpath = "//a[@id='downloadButton']";
    public static String uploadFileXpath = "//input[@id='uploadFile']";
    public static String uploadFilePathXpath = "//*[@id='uploadedFilePath']";
    public static String downloadPath = System.getProperty("user.home") + "/Downloads";
    public static String uploadFilePath = System.getProperty("user.dir") + "/src/test/resources/testUploadFile.txt";
    private static final Logger logger = LoggerFactory.getLogger(UploadDownloadPage.class);

    public void clickDowloadLink(){

        WebElement downloadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(downloadXpath)));
        downloadElement.click();
    }

    public File verifyDownloadedImage() {
        String downloadedFileName = "sampleFile.jpeg";
        File downloadedFile = new File(downloadPath + "/" + downloadedFileName);
        int timeoutInSeconds = 10;

        for (int i = 0; i < timeoutInSeconds; i++) {
            if (downloadedFile.exists()) {
                return downloadedFile;
            }
            try {
                Thread.sleep(1000); // Wait 1 second before retrying
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        logger.info("File not found after waiting: " + downloadedFile.getAbsolutePath());
        return downloadedFile;

    }


    public void uploadFile(){
        WebElement uploadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uploadFileXpath)));
        uploadElement.sendKeys(uploadFilePath);
    }

    public String getUploadedFilePath() {
        WebElement uploadFilePathelement = driver.findElement(By.xpath(uploadFilePathXpath));
        return uploadFilePathelement.getText();
    }
}
