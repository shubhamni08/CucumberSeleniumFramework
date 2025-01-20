package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features="src/test/resources/features",     //path of the features files
        glue = {"utility","stepDefinition"},         // Path to your step definition package
        plugin = {"pretty", "html:target/cucumber-reports.html"}    //json, html, junit, pretty
)
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}



