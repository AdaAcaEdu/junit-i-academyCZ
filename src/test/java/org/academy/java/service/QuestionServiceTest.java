package org.academy.java.service;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

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
        //TODO create junit test with mockito
    }
}