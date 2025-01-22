package stepDefinition;

import Base.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import pages.PracticeFormPage;
import utility.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PracticeFormsSteps extends BaseTest {

    public PracticeFormsSteps() {
        super();
        practiceFormPage = new PracticeFormPage();
    }

    private static final Logger logger = LoggerFactory.getLogger(PracticeFormsSteps.class);
    private final PracticeFormPage practiceFormPage;

    @When("I fill out the form with valid details:")
    public void i_fill_out_the_form_with_valid_details(DataTable dataTable) {
        List<String> fields = dataTable.column(0);
        List<String> values = dataTable.column(1);
        AtomicInteger index = new AtomicInteger(0);
        fields.forEach( field ->
                practiceFormPage.fillStudentRegistrationForm(field, values.get(index.getAndIncrement()))
        );
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
