package stepDefinition;

import Base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Alerts_Windows_FramesPage;

import java.time.Duration;
import java.util.NoSuchElementException;


public class AlertWindowsSteps extends BaseTest {
//    Alerts_Windows_FramesPage alertWindowsFramesPage;
    private Alerts_Windows_FramesPage alertsWindowsPage;
    private WebDriverWait wait;
    private Alert alert;

    @When("I open the {string} dialog")
    public void I_open_the_dialog(String buttonLabel) {
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        alertsWindowsPage.openModal(buttonLabel);
    }

    @Then("I should see the dialog content as {string}")
    public void I_should_see_the_dialog_content_as(String expectedContent) {
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        Assert.assertEquals(expectedContent, alertsWindowsPage.getModalContent());
    }

    @Then("I should see the dialog content starting with {string}")
    public void I_should_see_the_dialog_content_starting_with(String expectedStart) {
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        Assert.assertTrue(alertsWindowsPage.getModalContent().startsWith(expectedStart));
    }

    @Then("I should be able to close it by clicking the {string} button")
    public void I_should_be_able_to_close_it_by_clicking_the_button(String buttonLabel) {
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        alertsWindowsPage.closeModal(buttonLabel);
        Assert.assertTrue(alertsWindowsPage.isModalVisible());
    }

    @When("I click on {string} click me Button")
    public void i_click_on_clickme_button(String alertBtn) throws InterruptedException{
        // Click the alert button
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        alertsWindowsPage.clickAlertButton(alertBtn);
    }

    @Then("alert with the message {string} should appear")
    public void alert_with_the_message_should_appear(String expectedAlertMessage){
        alertsWindowsPage = new Alerts_Windows_FramesPage();
        String actualMessage = alertsWindowsPage.alertMessage(); // This will now handle the alert

        // Assert that the alert message matches the expected message
        Assert.assertEquals(actualMessage, expectedAlertMessage, "Alert message mismatch");
    }

    @And("I accept the alert")
    public void acceptAlert(){
        try{
            // Explicit wait for alert to be present and accept it
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());  // Wait for alert to be present
            alert = driver.switchTo().alert();
//            System.out.println(alert.getText());
            alert.accept();
        }catch(TimeoutException e){
            System.out.println("Alert did not appear within the expected time.");
        }
    }

    @And("I should verify the URL is {string}")
    public void I_should_verify_the_URL_is(String expectedURL) {
        Assert.assertEquals(expectedURL, alertsWindowsPage.getCurrentURL());
    }

    @When("I trigger a missing or disabled alert")
    public void I_trigger_a_missing_or_disabled_alert() {
        try {
            alertsWindowsPage.triggerNonExistentAlert();
        } catch (NoSuchElementException e) {
            System.out.println("Handled missing alert: " + e.getMessage());
        }
    }

    @When("I trigger the confirm alert and dismiss it")
    public void I_trigger_the_confirm_alert_and_dismiss_it() {
        alertsWindowsPage.triggerConfirmAlert();
        alertsWindowsPage.dismissAlert();
    }

    @Then("I should be able to switch between all tabs and windows")
    public void I_should_be_able_to_switch_between_all_tabs_and_windows() {
        Assert.assertTrue(alertsWindowsPage.switchBetweenAllWindowsAndVerify());
    }
}
