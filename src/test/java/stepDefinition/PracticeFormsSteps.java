package stepDefinition;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PracticeFormPage;

public class PracticeFormsSteps extends BaseTest {

    public PracticeFormsSteps() {
        super();
        practiceFormPage = new PracticeFormPage();
    }

    private final PracticeFormPage practiceFormPage;

    @When("I fill out the form with valid details")
    public void i_fill_out_the_form_with_valid_details(DataTable dataTable) {
        // Extracting data from the table
//        var data = dataTable.asMaps(String.class, String.class).get(0);
//        formPage.fillOutForm(data.get("First Name"), data.get("Last Name"), data.get("Email"),
//                data.get("Gender"), data.get("Mobile Number"), data.get("Date of Birth"),
//                data.get("Subject"), data.get("Hobbies"), data.get("Picture"),
//                data.get("Address"), data.get("State"), data.get("City"));
    }

    @When("I submit the form")
    public void i_submit_the_form() {
//        formPage.submitForm();
    }

    @Then("I should see a confirmation message or the table should reflect the submission details")
    public void i_should_see_a_confirmation_message_or_the_table_should_reflect_the_submission_details() {
        // Wait for the confirmation and check if the submission was successful
//        assertTrue(formPage.isSubmissionSuccessful());
//        driver.quit();
    }


}
