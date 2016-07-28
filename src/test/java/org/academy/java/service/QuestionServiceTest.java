package org.academy.java.service;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Iterator;

public class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    AnswerRepository answerRepository;

    @Mock
    QuestionRepository questionRepository;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        Mockito.when(answerRepository.save(Matchers.any(Answer.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        Mockito.when(questionRepository.save(Matchers.any(Question.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Question question = new Question();
        Answer answer = new Answer();

        questionService.addAnswerToQuestion(question, answer);
        Assert.assertEquals(question.getAnswers().size(), 1);
        Assert.assertEquals(question.getAnswers().iterator().next(), answer);
    }

    @Test
    public void testMakeRadioAnswersForQuestion() throws Exception {
        Question question = new Question();
        questionService.makeRadioAnswersForQuestion(question, null);
        Iterator<Answer> itr = question.getAnswers().iterator();

        Assert.assertEquals(question.getAnswers().size(), 1);
        Assert.assertTrue(itr.next().isCorrect());

        question = new Question();
        question.setQuestionType(Question.QuestionType.CHECKBOX);
        question.getAnswers().add(new Answer().setId(1).setCorrect(true));
        question.getAnswers().add(new Answer().setId(2).setCorrect(true));
        questionService.makeRadioAnswersForQuestion(question, null);
        itr = question.getAnswers().iterator();

        Assert.assertEquals(2, question.getAnswers().size());
        Assert.assertTrue(itr.next().isCorrect());
        Assert.assertFalse(itr.next().isCorrect());

        question = new Question();
        question.setQuestionType(Question.QuestionType.CHECKBOX);
        question.getAnswers().add(new Answer().setId(1).setCorrect(false));
        question.getAnswers().add(new Answer().setId(2).setCorrect(true));
        questionService.makeRadioAnswersForQuestion(question, null);

        Assert.assertEquals(2, question.getAnswers().size());
        itr = question.getAnswers().iterator();
        Assert.assertFalse(itr.next().isCorrect());
        Assert.assertTrue(itr.next().isCorrect());

        question = new Question();
        question.setQuestionType(Question.QuestionType.CHECKBOX);
        question.getAnswers().add(new Answer().setId(1).setCorrect(true));
        question.getAnswers().add(new Answer().setId(2).setCorrect(false));
        questionService.makeRadioAnswersForQuestion(question, (long)2);

        Assert.assertEquals(2, question.getAnswers().size());
        itr = question.getAnswers().iterator();
        Assert.assertFalse(itr.next().isCorrect());
        Assert.assertTrue(itr.next().isCorrect());
    }


}