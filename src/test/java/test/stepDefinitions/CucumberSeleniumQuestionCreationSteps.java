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

    @Given("^user opens browser$")
    public void user_opens_browser() throws Throwable {
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

    @Given("^user navigates to homepage$")
    public void user_navigates_to_homepage() throws Throwable {
        seleniumTestHelper.navigateToHomepage();
    }

    @Given("^user logs in$")
    public void user_logged_in() throws Throwable {
        if (firstTest) {
            seleniumTestHelper.login();
            firstTest = false;
        }
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

    /** Assignment **/

    @When("^user deletes last question$")
    public void user_deletes_last_question() throws Throwable {
        nElementsBeforeDelete = seleniumTestHelper.getFirstInterviewQuestionDivsNumber();
        seleniumTestHelper.deleteFirstInterviewLastQuestion();
    }

    @Then("^number of questions decreases by one$")
    public void number_of_questions_decreases_by_one() throws Throwable {
        Assert.assertTrue(nElementsBeforeDelete - 1 == seleniumTestHelper.getFirstInterviewQuestionDivsNumber());
    }

    @When("^user deletes last answer in last question$")
    public void user_deletes_last_answer_in_last_question() throws Throwable {
        seleniumTestHelper.deleteFirstInterviewLastQuestionLastAnswer();
    }

    @Then("^number of answers in last question will be zero$")
    public void number_of_answers_in_last_question_will_be_zero() throws Throwable {
        Assert.assertTrue(seleniumTestHelper.getFirstInterviewLastQuestionAnswerDivsNumber() == 0);
    }

    /** End of assignment **/

}
