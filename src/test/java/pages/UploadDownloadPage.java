package pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class UploadDownloadPage extends BaseTest {

    public UploadDownloadPage(){
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public static String downloadXpath = "//a[@id='downloadButton']";

    public static String uploadFileXpath = "//input[@id='uploadFile']";

    public static String uploadFilePathXpath = "//*[@id='uploadedFilePath']";

    public static String downloadPath = System.getProperty("user.home") + "/Downloads";

    public static String uploadFilePath = System.getProperty("user.dir") + "/src/test/resources/testUploadFile.txt";

    public static WebDriverWait wait;

    public void clickDowloadLink(){

        WebElement downloadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(downloadXpath)));
        downloadElement.click();
    }

    public File verifyDownloadedImage() {
        String downloadedFileName = "sampleFile.jpeg";
        System.out.println(downloadPath+"/"+ downloadedFileName);
        return new File(downloadPath + "/" + downloadedFileName);
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
