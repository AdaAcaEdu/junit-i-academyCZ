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
public class CucumberSeleniumQuestionCreationSteps {

    private static SeleniumTestHelper seleniumTestHelper;

    private static boolean firstTest = true;

    private int nElementsBeforeDelete;

    @Given("^admin opens browser$")
    public void admin_opens_browser() throws Throwable {
        if (firstTest) {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public synchronized void run() {
                    seleniumTestHelper.closeBrowser();
                }
            });
            seleniumTestHelper = new SeleniumTestHelper();
            seleniumTestHelper.openBrowser();
        }
    }

    @Given("^admin navigates to homepage$")
    public void admin_navigates_to_homepage() throws Throwable {
        seleniumTestHelper.navigateToHomepage();
    }

    @Given("^admin logs in$")
    public void admin_logged_in() throws Throwable {
        if (firstTest) {
            seleniumTestHelper.login();
            firstTest = false;
        }
    }

    @When("^admin creates \"([^\"]*)\" question of type \"([^\"]*)\"$")
    public void admin_creates_question_of_type(String arg1, String arg2) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, arg2);
    }

    @When("^admin adds \"([^\"]*)\" answer to question$")
    public void admin_adds_answer_to_question(String arg1) throws Throwable {
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

    /** Assignment **/

    @When("^admin deletes last question$")
    public void admin_deletes_last_question() throws Throwable {
        nElementsBeforeDelete = seleniumTestHelper.getFirstInterviewQuestionDivsNumber();
        seleniumTestHelper.deleteFirstInterviewLastQuestion();
    }

    @Then("^number of questions decreases by one$")
    public void number_of_questions_decreases_by_one() throws Throwable {
        Assert.assertTrue(nElementsBeforeDelete - 1 == seleniumTestHelper.getFirstInterviewQuestionDivsNumber());
    }

    @When("^admin deletes last answer in last question$")
    public void admin_deletes_last_answer_in_last_question() throws Throwable {
        seleniumTestHelper.deleteFirstInterviewLastQuestionLastAnswer();
    }

    @Then("^number of answers in last question will be zero$")
    public void number_of_answers_in_last_question_will_be_zero() throws Throwable {
        Assert.assertTrue(seleniumTestHelper.getFirstInterviewLastQuestionAnswerDivsNumber() == 0);
    }

    /** End of assignment **/

}
