package test;

import org.academy.java.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@org.springframework.boot.test.WebIntegrationTest(value = "server.port=8080")
public class InterviewWebIntegrationTest {

    private static final String checkBoxQuestionText = "This is question with checkbox";
    private static final String textAreaQuestionText = "This is question with textarea";
    private static final String radioQuestionText = "This is question with radio";

    private static final String checkBoxAnswerText = "This is checkbox answer";
    private static final String radioAnswerText = "This is radio answer";

    private WebDriver driver;

    @Before
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testWeb() throws Exception {
        testLogin();
        testAddCheckboxQuestion();
        testAddCheckboxAnswer();
        testAddTextAreaQuestion();
        testAddRadioQuestion();
        testAddRadioAnswer();
    }

    @After
    public void closeDriver() {
        driver.close();
    }

    private WebElement getFirstInterviewLastQuestionDiv() {
        WebElement interviewDiv = driver.findElement(By.className("interview-div"));
        List<WebElement> questionDivs = interviewDiv.findElements(By.className("question-div"));
        return questionDivs.get(questionDivs.size() - 1);
    }

    private String getFirstInterviewLastQuestionText() {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        return questionDiv.findElement(By.tagName("span")).getText();
    }

    private String getFirstInterviewLastQuestionLastAnswerText() {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        List<WebElement> answerDivs = questionDiv.findElements(By.className("answer-div"));
        WebElement answerDiv = answerDivs.get(answerDivs.size() - 1);
        return answerDiv.getText();
    }

    public void testLogin() throws Exception {
        driver.get("localhost:8080");
        driver.findElement(By.className("login-input")).sendKeys("admin");
        driver.findElement(By.className("password-input")).sendKeys("admin");
        driver.findElement(By.className("login-btn")).click();
        Thread.sleep(2000);
    }

    public void testAddCheckboxQuestion() throws Exception {
        driver.findElement(By.className("add-question-input")).sendKeys(checkBoxQuestionText);
        driver.findElement(By.className("add-question-btn")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getFirstInterviewLastQuestionText(), checkBoxQuestionText);
    }

    public void testAddCheckboxAnswer() throws Exception {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        questionDiv.findElement(By.className("add-answer-input")).sendKeys(checkBoxAnswerText);
        questionDiv.findElement(By.className("add-answer-btn")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getFirstInterviewLastQuestionLastAnswerText(), checkBoxAnswerText);
    }

    public void testAddTextAreaQuestion() throws Exception {
        driver.findElement(By.className("add-question-input")).sendKeys(textAreaQuestionText);
        driver.findElement(By.className("add-question-select")).click();
        driver.findElement(By.className("add-question-select")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        driver.findElement(By.className("add-question-btn")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getFirstInterviewLastQuestionText(), textAreaQuestionText);
    }

    public void testAddRadioQuestion() throws Exception {
        driver.findElement(By.className("add-question-input")).sendKeys(radioQuestionText);
        driver.findElement(By.className("add-question-select")).click();
        driver.findElement(By.className("add-question-select")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        driver.findElement(By.className("add-question-btn")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getFirstInterviewLastQuestionText(), radioQuestionText);
    }

    public void testAddRadioAnswer() throws Exception {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        questionDiv.findElement(By.className("add-answer-input")).sendKeys(radioAnswerText);
        questionDiv.findElement(By.className("add-answer-btn")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getFirstInterviewLastQuestionLastAnswerText(), radioAnswerText);
    }
}
