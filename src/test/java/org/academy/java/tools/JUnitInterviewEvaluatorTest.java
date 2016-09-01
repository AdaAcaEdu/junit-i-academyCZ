package org.academy.java.tools;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.junit.*;

public class JUnitInterviewEvaluatorTest {

    private static int testIndex = 1;

    private InterviewEvaluator interviewEvaluator;
    private Interview interview;

    private Question checkboxQuestion;
    private Question textQuestion;
    private Question radioQuestion;

    private Answer a1;
    private Answer a2;
    private Answer a3;

    private void setUpRadioQuestionInterview() {
        interview.getQuestions().clear();
        checkboxQuestion.getAnswers().clear();

        interview.getQuestions().add(radioQuestion);
        checkboxQuestion.getAnswers().add(a1);
        checkboxQuestion.getAnswers().add(a2);
    }

    private void setUpCheckboxQuestionInterview() {
        interview.getQuestions().clear();
        radioQuestion.getAnswers().clear();

        interview.getQuestions().add(checkboxQuestion);
        checkboxQuestion.getAnswers().add(a1);
        checkboxQuestion.getAnswers().add(a2);
    }

    private void setUpAllQuestionTypeInterview() {
        interview.getQuestions().clear();
        radioQuestion.getAnswers().clear();
        checkboxQuestion.getAnswers().clear();
        textQuestion.getAnswers().clear();

        interview.getQuestions().add(checkboxQuestion);
        interview.getQuestions().add(textQuestion);
        interview.getQuestions().add(radioQuestion);

        checkboxQuestion.getAnswers().add(a1);
        textQuestion.getAnswers().add(a2);
        radioQuestion.getAnswers().add(a3);
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("### Before all tests ###\n");
    }

    @Before
    public void before() {
        System.out.println("Before test #" + testIndex);

        interviewEvaluator = new InterviewEvaluator();
        interview = new Interview().setId(0);

        radioQuestion = new Question().setQuestionType(Question.QuestionType.RADIO).setId(0);
        checkboxQuestion = new Question().setQuestionType(Question.QuestionType.CHECKBOX).setId(1);
        textQuestion = new Question().setQuestionType(Question.QuestionType.TEXT_AREA).setId(2);

        a1 = new Answer().setId(0);
        a2 = new Answer().setId(1);
        a3 = new Answer().setId(2);
    }

    @Test
    public void evaluateRadioQuestionCorrectAnswerChosenTest() {
        System.out.println("Inside 'evaluateRadioQuestionCorrectAnswerChosenTest'");
        setUpRadioQuestionInterview();

        a1.setChosen(true).setCorrect(true);
        a2.setChosen(false).setCorrect(false);
        Assert.assertEquals(1, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateRadioQuestionIncorrectAnswerChosenTest() {
        System.out.println("Inside 'evaluateRadioQuestionCorrectAnswerChosenTest'");
        setUpRadioQuestionInterview();

        a1.setChosen(true).setCorrect(false);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateRadioQuestionAnswerNotChosenTest() {
        System.out.println("Inside 'evaluateRadioQuestionAnswerNotChosenTest'");
        setUpRadioQuestionInterview();

        a1.setChosen(false).setCorrect(false);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    /*********************************** Assignment *******************************************************************/

    @Test
    public void evaluateCheckboxQuesionCorrectAnswersChosen() {
        setUpCheckboxQuestionInterview();
        a1.setChosen(true).setCorrect(true);
        a2.setChosen(true).setCorrect(true);
        Assert.assertEquals(1, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateCheckboxQuestionAnswerNotChosenWithoutCorrectTest() {
        setUpCheckboxQuestionInterview();
        a1.setChosen(false).setCorrect(false);
        a2.setChosen(false).setCorrect(false);
        Assert.assertEquals(1, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateCheckboxQuestionAnswerNotChosenWithCorrectTest() {
        setUpCheckboxQuestionInterview();
        a1.setChosen(false).setCorrect(false);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateCheckboxQuestionCorrectAndIncorrectAnswerChosenTest() {
        setUpCheckboxQuestionInterview();
        a1.setChosen(true).setCorrect(false);
        a2.setChosen(true).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateCheckboxQuestionOneCorrectAnswerChosenTest() {
        setUpCheckboxQuestionInterview();
        a1.setChosen(true).setCorrect(true);
        a2.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    /*********************************** End of assignment ************************************************************/

    @Test
    public void evaluateInterviewAllAnswersCorrectTest() {
        System.out.println("Inside 'evaluateInterviewAllAnswersCorrectTest'");
        setUpAllQuestionTypeInterview();

        a1.setChosen(true).setCorrect(true);
        a2.setChosen(true).setCorrect(true);
        a3.setChosen(true).setCorrect(true);
        Assert.assertEquals(3, interviewEvaluator.evaluateInterview(interview));
    }

    @Test
    public void evaluateInterviewAllAnswersIncorrectTest() {
        System.out.println("Inside 'evaluateInterviewAllAnswersIncorrectTest'");
        setUpAllQuestionTypeInterview();

        a1.setChosen(false).setCorrect(true);
        a2.setChosen(false).setCorrect(false);
        a3.setChosen(false).setCorrect(true);
        Assert.assertEquals(0, interviewEvaluator.evaluateInterview(interview));
    }

    @After
    public void after() {
        System.out.println("After test #" + testIndex + "\n");
        testIndex++;
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("### After all tests ###");
    }
}
