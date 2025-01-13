package stepDefinition;

import Base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.WidgetsPage;

public class WidgetsSteps extends BaseTest {

    public WidgetsSteps(){
        super();
        widgetsPage = new WidgetsPage();
    }

    private final WidgetsPage widgetsPage;

    @When("I expand the {string} section")
    public void i_expand_the_section(String section) {
        widgetsPage.expandAccordionSection(section);
    }

    @Then("the content of {string} should be displayed")
    public void the_content_of_section_should_be_displayed(String sectionName) {
        Assert.assertTrue(widgetsPage.isAccordionContentDisplayed(sectionName));
    }

    @When("I move the slider to {string}")
    public void i_move_the_slider_to(String sliderValue) {
        widgetsPage.moveSliderToValue(sliderValue);
    }

    @Then("the slider value should be {string}")
    public void the_slider_value_should_be(String expectedValue){
        String actualValue = widgetsPage.getSliderValue();
        Assert.assertEquals(expectedValue, actualValue,"Slider value did not update correctly.");
    }

    @When("I click on the {string} button")
    public void I_click_on_button(String buttonName) throws InterruptedException{
        widgetsPage.clickProgressBarButton(buttonName);
    }

    @Then("the progress bar should reach {string}")
    public void the_progress_bar_should_reach(String percentage) {
        Assert.assertTrue(widgetsPage.isProgressBarComplete(percentage));
    }

    @Then("I get progress bar value")
    public void i_get_progress_bar_value(){
        System.out.println("i_get_progress_bar_value: "+widgetsPage.getProgressBarValue());
    }

    @When("I click on the {string} tab")
    public void I_click_on_tab(String tabName) {
        System.out.println("I_click_on_tab: "+tabName);
        widgetsPage.clickOnTab(tabName);
    }

    @Then("the content of the {string} tab should be displayed")
    public void the_content_of_tab_should_be_displayed(String tabName) {
        Assert.assertTrue(widgetsPage.isTabContentDisplayed(tabName));
    }

    @Then("I click on the {string} tab it is disabled")
    public void i_click_on_the_tab_it_is_disabled(String tabName) {
        Assert.assertTrue( widgetsPage.isMoreButtonDisabled(tabName),"More tab is disabled");
    }
}
