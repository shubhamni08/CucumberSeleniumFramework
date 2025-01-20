package stepDefinition;

import Base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import utility.LoggerFactory;
import org.testng.Assert;
import pages.Alerts_Windows_FramesPage;

public class AlertWindowsSteps extends BaseTest {

    private final Alerts_Windows_FramesPage alertsWindowsPage;
    private static final Logger logger = LoggerFactory.getLogger(AlertWindowsSteps.class);

    public AlertWindowsSteps() {
        this.alertsWindowsPage = new Alerts_Windows_FramesPage();
    }

    @When("I open the {string} dialog")
    public void I_open_the_dialog(String buttonLabel) {
        alertsWindowsPage.openModal(buttonLabel);
    }

    @Then("I should see the dialog content as {string}")
    public void I_should_see_the_dialog_content_as(String expectedContent) {
        Assert.assertEquals(expectedContent, alertsWindowsPage.getModalContent());
    }

    @Then("I should see the dialog content starting with {string}")
    public void I_should_see_the_dialog_content_starting_with(String expectedStart) {
        Assert.assertTrue(alertsWindowsPage.getModalContent().startsWith(expectedStart));
    }

    @Then("I should be able to close it by clicking the {string} button")
    public void I_should_be_able_to_close_it_by_clicking_the_button(String buttonLabel) {
        alertsWindowsPage.closeModal(buttonLabel);
        Assert.assertTrue(alertsWindowsPage.isModalVisible());
    }

    @And("I should verify the URL is {string}")
    public void I_should_verify_the_URL_is(String expectedURL) {
        Assert.assertEquals(expectedURL, alertsWindowsPage.getCurrentURL());
    }

    @Then("I should be able to switch between all tabs and windows")
    public void I_should_be_able_to_switch_between_all_tabs_and_windows() {
        Assert.assertTrue(alertsWindowsPage.switchBetweenAllWindowsAndVerify());
    }

    //alertPage methods
    @When("I click on {string} click me Button")
    public void iClickOnButton(String buttonName) {
        dismissUnexpectedAlert(); // Handle unexpected alerts
        alertsWindowsPage.clickAlertButton(buttonName);
    }

    @Then("alert with the message {string} should appear")
    public void alertWithTheMessageShouldAppear(String expectedMessage) {
        String actualMessage = alertsWindowsPage.getAlertMessageSafely();
        Assert.assertEquals(actualMessage, expectedMessage, "Alert message mismatch");
    }

    @And("I accept the alert")
    public void iAcceptTheAlert() {
        alertsWindowsPage.acceptAlertSafely();
    }

    @And("I dismiss the alert")
    public void iDismissTheAlert() {
        alertsWindowsPage.dismissAlertSafely();
    }

    @Then("the confirmation result should be {string}")
    public void theConfirmationResultShouldBe(String expectedResult) {
        String actualResult = alertsWindowsPage.getConfirmationResult();
        Assert.assertEquals(actualResult, expectedResult, "Confirmation result mismatch");
    }

    @When("I enter {string} into the alert")
    public void iEnterIntoTheAlert(String inputText) {
        alertsWindowsPage.enterTextInPromptAlertSafely(inputText);
    }

    @Then("the prompt result should be {string}")
    public void thePromptResultShouldBe(String expectedResult) {
        String actualResult = alertsWindowsPage.getPromptResult();
        Assert.assertEquals(actualResult, expectedResult, "Prompt result mismatch");
    }

}
