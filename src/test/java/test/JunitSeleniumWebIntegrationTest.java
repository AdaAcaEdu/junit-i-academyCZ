package test;

import org.academy.java.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.integrationTestTools.SeleniumTestHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@org.springframework.boot.test.WebIntegrationTest(value = "server.port=8080")
public class JunitSeleniumWebIntegrationTest {

    private static final String checkBoxQuestionText = "This is question with checkbox";
    private static final String textAreaQuestionText = "This is question with textarea";
    private static final String radioQuestionText = "This is question with radio";

    private static final String checkBoxAnswerText = "This is checkbox answer";
    private static final String radioAnswerText = "This is radio answer";

    private WebDriver driver;

    private SeleniumTestHelper seleniumTestHelper;

    @Before
    public void initDriver() throws Exception{
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        seleniumTestHelper = new SeleniumTestHelper(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("localhost:8080");
        driver.findElement(By.className("login-input")).sendKeys("admin");
        driver.findElement(By.className("password-input")).sendKeys("admin");
        driver.findElement(By.className("login-btn")).click();
        Thread.sleep(2000);
    }

    @Test
    public void testCreateTextQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(textAreaQuestionText, SeleniumTestHelper.TEXTAREA_INDEX);
        Thread.sleep(2000);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), textAreaQuestionText);
    }

    @Test
    public void testCreateRadioQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(radioQuestionText, SeleniumTestHelper.RADIO_INDEX);
        Thread.sleep(2000);

        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(radioAnswerText);
        Thread.sleep(2000);

        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), radioQuestionText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), radioAnswerText);
    }

    @Test
    public void testCreateCheckboxQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        Thread.sleep(2000);

        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(checkBoxAnswerText);
        Thread.sleep(2000);

        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), checkBoxAnswerText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), checkBoxQuestionText);
    }

    @After
    public void closeDriver() {
        driver.close();
    }
}
