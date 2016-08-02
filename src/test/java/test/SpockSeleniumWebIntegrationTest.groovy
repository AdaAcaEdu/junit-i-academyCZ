package test

import org.academy.java.Application
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification
import test.integrationTestTools.SeleniumTestHelper

@SpringApplicationConfiguration(classes = Application.class)
@org.springframework.boot.test.WebIntegrationTest(value = "server.port=8080")
class SpockSeleniumWebIntegrationTest extends Specification {

    private SeleniumTestHelper seleniumTestHelper;

    def setup() {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
        seleniumTestHelper.login();
    }

    def "Test radio question creation"() {
        when:
        seleniumTestHelper.createQuestionInFirstInterview(questionText, index);
        index != SeleniumTestHelper.TEXTAREA_INDEX ? seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(answerText) : true;

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionText() == questionText;
        index != SeleniumTestHelper.TEXTAREA_INDEX ? seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText() == answerText : true;

        where:
        questionText | answerText | index
        "This is question with checkbox" | "This is checkbox answer" | SeleniumTestHelper.CHECKBOX_INDEX
        "This is question with textarea" | null | SeleniumTestHelper.TEXTAREA_INDEX
        "This is question with radio" | "This is radio answer" | SeleniumTestHelper.RADIO_INDEX

    }

    def cleanup() {
        seleniumTestHelper.closeBrowser();
    }
}
