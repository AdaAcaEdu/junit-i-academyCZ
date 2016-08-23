package org.academy.java.service;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RunWith(Parameterized.class)
public class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    AnswerRepository answerRepository;

    @Mock
    QuestionRepository questionRepository;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { true , true, null, true, false },
                { false, true, null, false, true },
                { true, false, (long)2, false, true }
        });
    }

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    private boolean firstAnswerCorrect;
    private boolean secondAnsweCorrect;
    private Long correctAnswerIndex;
    private boolean expectedFirstAnswerCorrect;
    private boolean expectedSecondAnswerCorrect;

    public QuestionServiceTest(boolean firstAnswerCorrect, boolean secondAnsweCorrect, Long correctAnswerIndex,
            boolean expectedFirstAnswerCorrect, boolean expectedSecondAnswerCorrect) {

        this.firstAnswerCorrect = firstAnswerCorrect;
        this.secondAnsweCorrect = secondAnsweCorrect;
        this.correctAnswerIndex = correctAnswerIndex;
        this.expectedFirstAnswerCorrect = expectedFirstAnswerCorrect;
        this.expectedSecondAnswerCorrect = expectedSecondAnswerCorrect;
    }

    @Test
    public void testMakeRadioAnswers() throws Exception {

        Question question = new Question();
        question.setQuestionType(Question.QuestionType.CHECKBOX);
        question.getAnswers().add(new Answer().setId(1).setCorrect(firstAnswerCorrect));
        question.getAnswers().add(new Answer().setId(2).setCorrect(secondAnsweCorrect));
        questionService.makeRadioAnswersForQuestion(question, correctAnswerIndex);

        Iterator<Answer> itr = question.getAnswers().iterator();
        Assert.assertEquals(2, question.getAnswers().size());
        Assert.assertEquals(question.getQuestionType(), Question.QuestionType.RADIO);
        Assert.assertEquals(expectedFirstAnswerCorrect, itr.next().isCorrect());
        Assert.assertEquals(expectedSecondAnswerCorrect, itr.next().isCorrect());
    }
}