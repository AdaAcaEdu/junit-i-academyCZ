package org.academy.java.tools;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.junit.*;

public class JUnitInterviewEvaluatorTest {

    private static InterviewEvaluator interviewEvaluator;
    private static Interview interview;

    private static Question checkboxQuestion;
    private static Question textQuestion;
    private static Question radioQuestion;

    private static Answer a1;
    private static Answer a2;
    private static Answer a3;

    private static int testIndex = 1;

    @BeforeClass
    public static void setup() {

        System.out.println("### Before all tests ###");

        interviewEvaluator = new InterviewEvaluator();

        interview = new Interview().setId(0);

        radioQuestion = new Question().setQuestionType(Question.QuestionType.RADIO).setId(0);
        checkboxQuestion = new Question().setQuestionType(Question.QuestionType.CHECKBOX).setId(1);
        textQuestion = new Question().setQuestionType(Question.QuestionType.TEXT_AREA).setId(2);

        a1 = new Answer().setId(0);
        a2 = new Answer().setId(1);
        a3 = new Answer().setId(2);
    }

    @Before
    public void clear() {

        System.out.println("Before test #" + testIndex);

        interview.getQuestions().clear();
        radioQuestion.getAnswers().clear();
        checkboxQuestion.getAnswers().clear();
        textQuestion.getAnswers().clear();
    }

    @Test
    public void EvaluateCheckboxQuestionCorrectAnswerChosenTest() {

        interview.getQuestions().add(checkboxQuestion);
        checkboxQuestion.getAnswers().add(a1);
        checkboxQuestion.getAnswers().add(a2);

        a1.setChosen(true).setCorrect(true);
        a2.setChosen(false).setCorrect(false);
        Assert.assertEquals(1, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void EvaluateCheckboxQuestionIncorrectAnswerChosenTest() {

        interview.getQuestions().add(checkboxQuestion);
        checkboxQuestion.getAnswers().add(a1);
        checkboxQuestion.getAnswers().add(a2);

        a1.setChosen(true).setCorrect(false);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void EvaluateCheckboxQuestionAnswerNotChosenTest() {

        interview.getQuestions().add(checkboxQuestion);
        checkboxQuestion.getAnswers().add(a1);
        checkboxQuestion.getAnswers().add(a2);

        a1.setChosen(false).setCorrect(false);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void EvaluateInterviewAllAnswersCorrectTest() {

        Interview interview = new Interview();

        interview.getQuestions().add(checkboxQuestion);
        interview.getQuestions().add(textQuestion);
        interview.getQuestions().add(radioQuestion);

        checkboxQuestion.getAnswers().add(a1);
        textQuestion.getAnswers().add(a2);
        radioQuestion.getAnswers().add(a3);

        a1.setChosen(true).setCorrect(true);
        a2.setChosen(true).setCorrect(true);
        a3.setChosen(true).setCorrect(true);
        Assert.assertEquals(3, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void EvaluateInterviewAllAnswersIncorrectTest() {

        interview.getQuestions().add(checkboxQuestion);
        interview.getQuestions().add(textQuestion);
        interview.getQuestions().add(radioQuestion);

        checkboxQuestion.getAnswers().add(a1);
        textQuestion.getAnswers().add(a2);
        radioQuestion.getAnswers().add(a3);

        a1.setChosen(false).setCorrect(true);
        a2.setChosen(false).setCorrect(false);
        a3.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @After
    public void after() {
        System.out.println("After test #" + testIndex);
        testIndex++;
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("### After all tests ###");
    }
}
