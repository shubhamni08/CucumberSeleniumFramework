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

    @When("I fill out the form with valid details:")
    public void i_fill_out_the_form_with_valid_details(DataTable dataTable) {
        practiceFormPage.fillPracticeForm(dataTable);
    }

    @When("I submit the form")
    public void i_submit_the_form() {
        practiceFormPage.submitForm();
    }

    @Then("I should see a confirmation message or the table should reflect the submission details")
    public void i_should_see_a_confirmation_message_or_the_table_should_reflect_the_submission_details() {
        practiceFormPage.verifySubmission();
    }
}
