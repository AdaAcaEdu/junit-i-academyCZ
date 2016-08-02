package test;

import org.academy.java.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.integrationTestTools.SeleniumTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@org.springframework.boot.test.WebIntegrationTest(value = "server.port=8080")
public class JunitSeleniumWebIntegrationTest {

    private static final String checkBoxQuestionText = "This is question with checkbox";
    private static final String textAreaQuestionText = "This is question with textarea";
    private static final String radioQuestionText = "This is question with radio";

    private static final String checkBoxAnswerText = "This is checkbox answer";
    private static final String radioAnswerText = "This is radio answer";

    private SeleniumTestHelper seleniumTestHelper;

    @Before
    public void init() throws Exception{
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
        seleniumTestHelper.login();
    }

    @Test
    public void testCreateTextQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(textAreaQuestionText, SeleniumTestHelper.TEXTAREA_INDEX);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), textAreaQuestionText);
    }

    @Test
    public void testCreateRadioQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(radioQuestionText, SeleniumTestHelper.RADIO_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(radioAnswerText);

        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), radioQuestionText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), radioAnswerText);
    }

    @Test
    public void testCreateCheckboxQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(checkBoxAnswerText);

        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), checkBoxAnswerText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), checkBoxQuestionText);
    }

    @After
    public void closeDriver() {
        seleniumTestHelper.closeBrowser();
    }
}
