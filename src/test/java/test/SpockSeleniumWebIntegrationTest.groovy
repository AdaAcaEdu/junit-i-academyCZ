package test

import org.academy.java.Application
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import spock.lang.Specification
import test.integrationTestTools.SeleniumTestHelper

@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=8080")
class SpockSeleniumWebIntegrationTest extends Specification {

    private SeleniumTestHelper seleniumTestHelper;

    def setup() {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
        seleniumTestHelper.navigateToHomepage();
        seleniumTestHelper.login();
    }

    def "Test radio, checkbox and text question creation"() {

        when:
        seleniumTestHelper.createQuestionInFirstInterview(questionText, typeIndex);

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionText() == questionText;

        where:
        questionText | typeIndex
        "This is question with checkbox" | SeleniumTestHelper.CHECKBOX_INDEX
        "This is question with textarea" | SeleniumTestHelper.TEXTAREA_INDEX
        "This is question with radio" | SeleniumTestHelper.RADIO_INDEX
    }

    def "Test radio and checkbox answer creation"() {

        setup:
        seleniumTestHelper.createQuestionInFirstInterview(questionText, typeIndex);

        when:
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(answerText);

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText() == answerText;

        where:
        questionText | answerText | typeIndex
        "This is question with checkbox" | "This is checkbox answer" | SeleniumTestHelper.CHECKBOX_INDEX
        "This is question with radio" | "This is radio answer" | SeleniumTestHelper.RADIO_INDEX
    }

    def cleanup() {
        seleniumTestHelper.closeBrowser();
    }
}
