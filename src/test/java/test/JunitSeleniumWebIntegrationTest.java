package test;

import org.academy.java.Application;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.integrationTestTools.SeleniumTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=8080")
public class JunitSeleniumWebIntegrationTest {

    private static final String checkBoxQuestionText = "This is question with checkbox";
    private static final String textAreaQuestionText = "This is question with textarea";
    private static final String radioQuestionText = "This is question with radio";

    private static final String checkBoxAnswerText = "This is checkbox answer";
    private static final String radioAnswerText = "This is radio answer";

    private static SeleniumTestHelper seleniumTestHelper;

    private static boolean firstTest = true;

    @BeforeClass
    public static void openBrowser() {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
    }

    @Before
    public void init() throws Exception{
        seleniumTestHelper.navigateToHomepage();
        if (firstTest) {
            seleniumTestHelper.login();
            firstTest = false;
        }
    }

    @Test
    public void testCreateTextQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(textAreaQuestionText, SeleniumTestHelper.TEXTAREA_INDEX);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), textAreaQuestionText);
    }

    @Test
    public void testCreateRadioQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(radioQuestionText, SeleniumTestHelper.RADIO_INDEX);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), radioQuestionText);
    }

    @Test
    public void testCreateCheckboxQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionText(), checkBoxQuestionText);
    }

    @Test
    public void testCreateRadioAnswer() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(radioQuestionText, SeleniumTestHelper.RADIO_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(radioAnswerText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), radioAnswerText);
    }

    @Test
    public void testCreateCheckboxAnswer() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(checkBoxAnswerText);
        Assert.assertEquals(seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText(), checkBoxAnswerText);
    }

    /** Assignment **/

    @Test
    public void testDeleteQuestion() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        int nElementsBeforeDelete = seleniumTestHelper.getFirstInterviewQuestionDivsNumber();
        seleniumTestHelper.deleteFirstInterviewLastQuestion();
        Assert.assertEquals(nElementsBeforeDelete - 1, seleniumTestHelper.getFirstInterviewQuestionDivsNumber());
    }

    @Test
    public void testDeleteAnswer() throws Exception{
        seleniumTestHelper.createQuestionInFirstInterview(checkBoxQuestionText, SeleniumTestHelper.CHECKBOX_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(checkBoxAnswerText);
        seleniumTestHelper.deleteFirstInterviewLastQuestionLastAnswer();
        Assert.assertEquals(0, seleniumTestHelper.getFirstInterviewLastQuestionAnswerDivsNumber());
    }

    /** End of assignment **/

    @AfterClass
    public static void closeDriver() {
        seleniumTestHelper.closeBrowser();
    }
}
