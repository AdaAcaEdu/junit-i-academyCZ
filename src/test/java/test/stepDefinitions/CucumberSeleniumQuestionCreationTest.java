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

    @Given("^user opened browser and went to \"([^\"]*)\"$")
    public void user_opened_browser_and_went_to(String arg1) throws Throwable {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
    }

    @Given("^user logged in$")
    public void user_logged_in() throws Throwable {
        seleniumTestHelper.login();
    }

    @When("^user creates text question \"([^\"]*)\"$")
    public void user_creates_text_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.TEXTAREA_INDEX);
    }

    @When("^user creates radio question \"([^\"]*)\"$")
    public void user_creates_radio_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.RADIO_INDEX);
    }

    @When("^user creates checkbox question \"([^\"]*)\"$")
    public void user_creates_checkbox_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.CHECKBOX_INDEX);
    }

    @When("^user adds answer \"([^\"]*)\" to question$")
    public void user_adds_answer_to_question(String arg1) throws Throwable {
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(arg1);
    }


    @Then("^answer \"([^\"]*)\" appears as last in question$")
    public void answer_appears_as_last_in_question(String arg1) throws Throwable {
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), arg1);
    }

    @Then("^question \"([^\"]*)\" appears as last in interview$")
    public void question_appears_as_last_in_interview(String arg1) throws Throwable {
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), arg1);
    }

    @Then("^close browser$")
    public void close_browser() throws Throwable {
        seleniumTestHelper.closeBrowser();
    }

}
