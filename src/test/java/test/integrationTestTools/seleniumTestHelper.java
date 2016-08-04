package test.integrationTestTools;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by stanislav.galfy on 1.8.2016.
 */
public class SeleniumTestHelper {

    public static int CHECKBOX_INDEX = 0;
    public static int TEXTAREA_INDEX = 1;
    public static int RADIO_INDEX = 2;

    private WebDriver driver;

    public SeleniumTestHelper() {
    }

    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("localhost:8080");
    }

    public void closeBrowser() {
        driver.close();
    }

    public WebElement getFirstInterviewLastQuestionDiv() {
        WebElement interviewDiv = driver.findElement(By.className("interview-div"));
        List<WebElement> questionDivs = interviewDiv.findElements(By.className("question-div"));
        return questionDivs.get(questionDivs.size() - 1);
    }

    public String getFirstInterviewLastQuestionText() {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        return questionDiv.findElement(By.tagName("span")).getText();
    }

    public String getFirstInterviewLastQuestionLastAnswerText() {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        List<WebElement> answerDivs = questionDiv.findElements(By.className("answer-div"));
        WebElement answerDiv = answerDivs.get(answerDivs.size() - 1);
        return answerDiv.getText();
    }

    public void login() throws Exception {
        driver.findElement(By.className("login-input")).sendKeys("admin");
        driver.findElement(By.className("password-input")).sendKeys("admin");
        driver.findElement(By.className("login-btn")).click();
        Thread.sleep(2000);
    }

    public void createQuestionInFirstInterview(String text, int typeIndex) throws Exception {
        WebElement interviewDiv = driver.findElement(By.className("interview-div"));
        interviewDiv.findElement(By.className("add-question-input")).sendKeys(text);
        interviewDiv.findElement(By.className("add-question-select")).click();
        IntStream.range(0, typeIndex).forEach(i -> interviewDiv.findElement(By.className("add-question-select")).sendKeys(Keys.ARROW_DOWN));
        interviewDiv.findElement(By.className("add-question-select")).sendKeys(Keys.ENTER);
        interviewDiv.findElement(By.className("add-question-btn")).click();
        Thread.sleep(2000);
    }

    public void createAnswerInFirstInterviewLastQuestion(String text) throws Exception {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        questionDiv.findElement(By.className("add-answer-input")).sendKeys(text);
        questionDiv.findElement(By.className("add-answer-btn")).click();
        Thread.sleep(2000);
    }
}
