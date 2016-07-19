package org.academy.java.service;


import org.academy.java.entity.Question;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.IntegrationTest;

import java.util.List;

import static org.academy.java.entity.Question.QuestionType.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CriteriaApiQuestionServiceTest extends IntegrationTest {

    @Autowired
    QuestionService questionService;

    @Test
    public void testGetQuestionsByTypes() throws Exception {
        List<Question> questions = questionService.getQuestionsByTypes(CHECKBOX, RADIO, TEXT_AREA);
        assertThat(questions.size(), is(3));
    }}
