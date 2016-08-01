package test.integrationTestTools;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by stanislav.galfy on 1.8.2016.
 */
public class SeleniumTestHelper {

    public static int CHECKBOX_INDEX = 0;
    public static int TEXTAREA_INDEX = 1;
    public static int RADIO_INDEX = 2;

    public WebDriver driver;

    public SeleniumTestHelper(WebDriver driver) {
        this.driver = driver;
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

    public void createQuestionInFirstInterview(String text, int typeIndex) {
        driver.findElement(By.className("add-question-input")).sendKeys(text);
        driver.findElement(By.className("add-question-select")).click();
        IntStream.range(0, typeIndex).forEach(i -> driver.findElement(By.className("add-question-select")).sendKeys(Keys.ARROW_DOWN));
        driver.findElement(By.className("add-question-select")).sendKeys(Keys.ENTER);
        driver.findElement(By.className("add-question-btn")).click();
    }

    public void createAnswerInFirstInterviewLastQuestion(String text) {
        WebElement questionDiv = getFirstInterviewLastQuestionDiv();
        questionDiv.findElement(By.className("add-answer-input")).sendKeys(text);
        questionDiv.findElement(By.className("add-answer-btn")).click();
    }
}
