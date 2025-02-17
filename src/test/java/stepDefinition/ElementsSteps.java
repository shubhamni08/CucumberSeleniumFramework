package stepDefinition;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Logger;
import utility.LoggerFactory;

public class ElementsSteps  extends BaseTest {

    public ElementsSteps(){
        super();
        homePage = new HomePage();
        textBoxPage = new TextBoxPage();
        checkBoxPage = new CheckBoxPage();
        radioButtonPage = new RadioButtonPage();
        webTablesPage = new WebTablesPage();
        linksPage = new LinksPage();
        uploadDownloadPage = new UploadDownloadPage();
        dynamicProperties = new DynamicProperties();
        alertsWindowsFramesPage = new Alerts_Windows_FramesPage();
    }

    private final HomePage homePage;
    private final TextBoxPage textBoxPage;
    private final CheckBoxPage checkBoxPage;
    private final RadioButtonPage radioButtonPage;
    private final WebTablesPage webTablesPage;
    private final LinksPage linksPage;
    private final UploadDownloadPage uploadDownloadPage;
    private final DynamicProperties dynamicProperties;
    private final Alerts_Windows_FramesPage alertsWindowsFramesPage;
    private static final Logger log = LoggerFactory.getLogger(ElementsSteps.class);

    @Given( "I am on the DemoQA homepage")
    public void I_am_on_the_DemoQA_homepage(){
        log.info("I_am_on_the_DemoQA_homepage method");
        driver.get("https://demoqa.com/");
    }

    @When("I navigate to the {string} section")
    public void I_navigate_to_the_Elements_section(String cardName){
        log.info("Clicking on card: {}" , cardName);
        homePage.click_on_card_by_name(cardName);
    }

    @And("I click on {string}")
    public void I_click_on_MenuItem(String menuItem){
        log.info("I_click_on_MenuItem: {}", menuItem);
        homePage.click_on_menu_items_from_cards(menuItem);
    }

    @When("I fill the form with:")
    public void I_fill_the_form_with(DataTable dataTable){
        List<String> fields = dataTable.column(0);
        List<String> values = dataTable.column(1);
        AtomicInteger index = new AtomicInteger(0);
        fields.forEach( field ->
                textBoxPage.fillTextBoxForm(field, values.get(index.getAndIncrement()))
        );
    }

    @Then("I click on submit button")
    public void I_click_on_submit_button() {
        log.info("I_click_on_submit_button method");
        textBoxPage.clickSubmitButton();
    }

    @Then("the output should display:")
    public void the_output_should_display(DataTable expectedOutput) {
        List<Map<String, String>> expectedData = expectedOutput.asMaps(String.class, String.class);

        for (Map<String, String> field : expectedData) {
            String fieldName = field.get("Field");
            String expectedValue = field.get("Value");

            // Get the locator from the Fields enum
            Fields fieldEnum = Fields.getLocatorByFieldName(fieldName);

            // Fetch actual value using the locator
            String actualValue = textBoxPage.getOutputText(fieldEnum.getLocator());

            log.info("Validating field: {} | Expected: {} | Actual: {}", fieldName, expectedValue, actualValue);
            // Assert that the actual value matches the expected value
            Assert.assertEquals(actualValue, expectedValue, fieldName + " output mismatch.");
        }
    }

    @Then("no output should be displayed")
    public void no_output_should_be_displayed() {
        log.info("no_output_should_be_displayed method");
        Assert.assertFalse( textBoxPage.isOutputSectionDisplayed(),"");
        Assert.assertEquals(textBoxPage.getOutputText(),"" );
    }

    @And("I click on ExpandAll button")
    public void I_click_on_ExpandAll_button() {
        log.info("I_click_on_ExpandAll_button method");
        checkBoxPage.clickExpandAll();
    }

    @When("I click on CollapseAll button")
    public void I_click_on_CollapseAll_button() {
        log.info("I_click_on_CollapseAll_button method");
        checkBoxPage.clickCollapseAll();
    }

    @And("I select the {string} checkbox")
    public void I_select_the_checkbox(String checkboxName) throws InterruptedException{
        log.info("I_select_the_checkbox: {}",checkboxName);
        checkBoxPage.selectCheckBox(checkboxName);
    }

    @When("I select the following checkboxes:")
    public void i_select_the_following_checkboxes(DataTable dataTable){
        log.info("I_Select_the_following_checkboxes: {}",dataTable);
        checkBoxPage.selectMultipleCheckBoxes(dataTable);
    }

    @Then("the success message {string} should be displayed")
    public void the_success_message_should_be_displayed(String expectedMessage) {
        log.info("Validating success message: {}" , expectedMessage);
        String actualValues = checkBoxPage.successMessageActualResult(expectedMessage);
        Assert.assertEquals("You have selected : "+actualValues,expectedMessage,"Success message mismatch" );
    }

    @Then("all items in the tree structure should be {string}")
    public void all_items_in_the_tree_structure_should_be(String state) {
        log.info("Verifying tree structure: {}", state);
        List<WebElement> items = driver.findElements(By.xpath("//span[@class='rct-title']"));
        if (state.equals("visible")) {
            Assert.assertFalse(items.isEmpty(), "Items should be visible");
        } else if (state.equals("collapsed")) {
            log.info(items.size()==1);
            Assert.assertTrue(items.size()<=1,"Items should not be expanded");
        }
    }


    @When("I select the {string} radio button")
    public void I_select_the_button_option(String optionName){
        log.info("I_select_the_radio_button_option: {}",optionName);
        radioButtonPage.selectRadioButtonOption(optionName);
    }

    @Then("the message {string} should be displayed")
    public void the_Message_Should_Be_Displayed(String expectedMessage) {
        log.info("Verifying message: {}", expectedMessage);
        String actualMessage = radioButtonPage.getClickMessage(expectedMessage); // Pass the expected message
        log.info(actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Message does not match!");
    }

    @Then("the message You have selected {string} should be displayed")
    public void the_Message_you_have_selected_Should_Be_Displayed(String expectedMessage) {
        log.info("the_Message_Should_Be_Displayed: {}", expectedMessage);
        String actualMessage = radioButtonPage.getClickMessage(expectedMessage); // Pass the expected message
        log.info(actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Message does not match!");
    }

    @Then("the {string} radio button is disabled")
    public void the_Radio_Button_is_disabled(String optionName) {
        if(optionName.equals("No")){
            Assert.assertTrue(radioButtonPage.isNoRadioButtonDisabled());
        }
    }

    @When("I click on {string} button")
    public void I_click_on_button(String buttonName) {
        log.info("I_click_on_button: {}",buttonName);
        radioButtonPage.clickButton(buttonName);
    }


    @When("I click on the {string} button.")
    public void clickButton(String buttonName) {
        if ("Add".equalsIgnoreCase(buttonName)) {
            webTablesPage.clickAddButton();
        } else {
            throw new IllegalArgumentException("Unsupported button: " + buttonName);
        }
    }

    @When("I fill the registration form with the following details:")
    public void fillRegistrationForm(DataTable dataTable) {
        webTablesPage.fillTextBoxForm(dataTable);
    }

    @When("I submit the registration form")
    public void submitRegistrationForm() {
        webTablesPage.submitForm();
    }

    @Then("I verify the entry {string} exists in the table")
    public void verifyEntryExists(String entry){
        boolean isPresent = webTablesPage.isEntryPresent(entry);
        log.info(isPresent);
        Assert.assertTrue(isPresent,"Expected entry '" + entry + "' to exist in the table, but it was not found.");
    }

    @When("I edit the entry {string} with the following details:")
    public void editEntry(String identifier, DataTable updatedData) {
        Assert.assertTrue(webTablesPage.isEntryPresent(identifier),"Cannot edit entry '" + identifier + "' because it does not exist.");
        webTablesPage.editEntry(identifier, updatedData);
    }

    @When("I delete the entry {string}")
    public void deleteEntry(String identifier) {
        Assert.assertTrue(webTablesPage.isEntryPresent(identifier),"Cannot delete entry '" + identifier + "' because it does not exist.");
        webTablesPage.deleteEntry(identifier);
    }

    @Then("I verify the entry {string} no longer exists in the table")
    public void verifyEntryDoesNotExist(String identifier) {
        boolean isPresent = webTablesPage.isEntryPresent(identifier);
        Assert.assertFalse(isPresent,"Expected entry '" + identifier + "' to not exist in the table, but it was found.");
    }


    @Given("I am on the Links page")
    public void i_am_on_the_Links_page() {
        log.info("i_am_on_the_Links_page method");
        homePage.click_on_card_by_name("Elements");
        homePage.click_on_menu_items_from_cards("Links");
    }

    @When("I click on the {string} link")
    public void I_click_on_the_link(String link){
        log.info("I_click_on_the_link: {}",link);
        linksPage.clickLink(link);
    }

    @Then("I should be redirected to {string}")
    public void I_should_be_redirected_to(String expectedUrl){
        log.info("I_should_be_redirected_to: {}",expectedUrl);
        Assert.assertEquals(expectedUrl, linksPage.getCurrentURL());
    }

    @Then("I should see the API response message {string}")
    public void I_should_see_the_API_response_message(String expectedMessage) {
        log.info("I_should_see_the_API_response_message: {}",expectedMessage);
        String actualMessage = linksPage.getAPIResponseText();
        log.info("Actual message: {}",actualMessage);
        String[] expectedStatusCodeRes = expectedMessage.split(" ");

        String actualStatusCode = driver.findElement(By.xpath("//p[@id='linkResponse']/b[1]")).getText();
        Assert.assertEquals(actualStatusCode,expectedStatusCodeRes[0]);

        String actualStatusResponse = driver.findElement(By.xpath("//p[@id='linkResponse']/b[2]")).getText();
        Assert.assertEquals(actualStatusResponse,expectedStatusCodeRes[1]);
    }

    @And("I download a file")
    public void i_download_a_file(){
        log.info("I download a file");
        uploadDownloadPage.clickDowloadLink();
    }

    @Then("the file should be downloaded in the local folder")
    public void the_file_should_be_downloaded_in_the_local_folder(){
        log.info("The File Should be downloaded in the Local Folder");
        File file = uploadDownloadPage.verifyDownloadedImage();
        System.out.println("File path checked: " + file.getAbsolutePath());
        Assert.assertTrue(file.exists(), "Downloaded file not found in the expected location");
    }

    @When("I upload a file")
    public void i_upload_a_file(){
        log.info("I upload a file");
        uploadDownloadPage.uploadFile();
    }

    @Then("the uploaded file path should be displayed")
    public void theUploadedFilePathShouldBeDisplayed() {
        String uploadedFilePath = uploadDownloadPage.getUploadedFilePath();
        Assert.assertTrue(uploadedFilePath.contains("testUploadFile.txt"), "Uploaded file path is not displayed correctly");
    }

    @Then("the {string} button should be enabled after 5 seconds")
    public void the_button_should_be_enabled_after_5_seconds(String btn){
        Assert.assertTrue(dynamicProperties.buttonEnabledVisible(btn), btn+" button is not enabled after 5 seconds");
    }

    @Then("the {string} button should change color")
    public void the_button_should_change_color(String btn){
        dynamicProperties.btnChangeColor(btn);
    }

    @Then("the {string} button should become visible after 5 seconds")
    public void the_button_should_become_visible_after_5_seconds(String btn){
        boolean actualResult = dynamicProperties.buttonEnabledVisible(btn);
        Assert.assertTrue(actualResult,btn+" Button is visible after 5 seconds");
    }

    @When("I open a {string}")
    public void i_open_a_new_window(String windowName){
        log.info("I open a new Window");
        alertsWindowsFramesPage.verifyNewWindow(windowName);
    }

}

