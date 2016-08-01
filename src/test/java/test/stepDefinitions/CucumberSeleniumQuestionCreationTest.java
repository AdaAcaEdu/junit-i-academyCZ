package test.stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.academy.java.Application;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.integrationTestTools.SeleniumTestHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest(value = "server.port=8080")
public class CucumberSeleniumQuestionCreationTest {

    private WebDriver driver;

    private SeleniumTestHelper seleniumTestHelper;

    @Given("^user opened browse and went to \"([^\"]*)\"$")
    public void user_opened_browse_and_went_to(String arg1) throws Throwable {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        seleniumTestHelper = new SeleniumTestHelper(driver);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get("localhost:8080");
    }

    @Given("^user logged in$")
    public void user_logged_in() throws Throwable {
        driver.findElement(By.className("login-input")).sendKeys("admin");
        driver.findElement(By.className("password-input")).sendKeys("admin");
        driver.findElement(By.className("login-btn")).click();
        Thread.sleep(2000);
    }

    @When("^user creates text question \"([^\"]*)\"$")
    public void user_creates_text_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.TEXTAREA_INDEX);
        Thread.sleep(2000);
    }

    @When("^user creates radio question \"([^\"]*)\"$")
    public void user_creates_radio_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.RADIO_INDEX);
        Thread.sleep(2000);
    }

    @When("^user creates checkbox question \"([^\"]*)\"$")
    public void user_creates_checkbox_question(String arg1) throws Throwable {
        seleniumTestHelper.createQuestionInFirstInterview(arg1, SeleniumTestHelper.CHECKBOX_INDEX);
        Thread.sleep(2000);
    }

    @When("^user adds answer \"([^\"]*)\" to question$")
    public void user_adds_answer_to_question(String arg1) throws Throwable {
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(arg1);
        Thread.sleep(2000);
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
        driver.close();
    }

}
