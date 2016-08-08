package test.stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.academy.java.Application;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.integrationTestTools.SeleniumTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest(value = "server.port=8080")
public class CucumberSeleniumQuestionCreationTest {

    private SeleniumTestHelper seleniumTestHelper;

    @Given("^user opens browser$")
    public void user_opens_browser() throws Throwable {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
    }

    @Given("^user navigates to homepage$")
    public void user_navigates_to_homepage() throws Throwable {
        seleniumTestHelper.navigateToHomepage();
    }

    @Given("^user loggs in$")
    public void user_logged_in() throws Throwable {
        seleniumTestHelper.login();
}

    @When("^user creates \"([^\"]*)\" question of type (\\d+)$")
    public void user_creates_question_of_type(String arg1, int arg2) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, arg2);
    }

    @When("^user adds \"([^\"]*)\" answer to question$")
    public void user_adds_answer_to_question(String arg1) throws Throwable {
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(arg1);
    }

    @Then("^\"([^\"]*)\" question appears as last in interview$")
    public void question_appears_as_last_in_interview(String arg1) throws Throwable {
        Assert.assertTrue(seleniumTestHelper.getFirstInterviewLastQuestionText().equals(arg1));
    }

    @Then("^\"([^\"]*)\" answer appears as last in question$")
    public void answer_appears_as_last_in_question(String arg1) throws Throwable {
        Assert.assertTrue(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText().equals(arg1));
    }

    @When("^user adds none answer to question$")
    public void user_adds_none_answer_to_question() throws Throwable {
    }

    @Then("^none answer appears as last in question$")
    public void none_answer_appears_as_last_in_question() throws Throwable {
    }

    @Then("^user closes browser$")
    public void close_browser() throws Throwable {
        seleniumTestHelper.closeBrowser();
    }

}
