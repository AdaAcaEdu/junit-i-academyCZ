package org.academy.java.controller;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.service.QuestionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import test.IntegrationTest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateQuestionTest extends IntegrationTest {

    @Autowired
    QuestionService interviewService;

    @Test
    public void testCreateQuestion__REST_REST() throws Exception {

        Question question = new Question().setText("What is REST?").setPosition(1);

        Answer answer = new Answer().setText("REpresentational State Transfer");
        question.getAnswers().add(answer);


        MvcResult mvcResult = mockMvc.perform(post("/questions")
                .content(toJson(question))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) //TODO probably return 201 created
                .andReturn();

        Question returnedQuestion = toObject(mvcResult.getResponse().getContentAsString(), Question.class);

        assertThat(returnedQuestion.getId(), notNullValue());
        assertThat(returnedQuestion.getText(), is(question.getText()));
        assertThat(returnedQuestion.getPosition(), is(question.getPosition()));
    }


    @Test
    public void testCreateQuestion__Service_REST() throws Exception {

        Question question = new Question().setText("What is REST?").setPosition(1);

        Answer answer = new Answer().setText("REpresentational State Transfer");
        question.getAnswers().add(answer);

        Long id = interviewService.saveOrUpdateQuestion(question).getId();

        MvcResult mvcResult = mockMvc.perform(get("/question/" + id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        Question returnedQuestion = toObject(mvcResult.getResponse().getContentAsString(), Question.class);

        assertThat(returnedQuestion.getId(), notNullValue());
        assertThat(returnedQuestion.getText(), is(question.getText()));
        assertThat(returnedQuestion.getPosition(), is(question.getPosition()));
    }

    @Test
    public void testCreateQuestion__REST_Service() throws Exception {

        Question question = new Question().setText("What is REST?").setPosition(1);

        Answer answer = new Answer().setText("REpresentational State Transfer");
        question.getAnswers().add(answer);


        MvcResult mvcResult = mockMvc.perform(post("/questions")
                .content(toJson(question))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) //TODO probably return 201 created
                .andReturn();

        Long id = toObject(mvcResult.getResponse().getContentAsString(), Question.class).getId();


        Question returnedQuestion = interviewService.findQuestionById(id);

        assertThat(returnedQuestion.getId(), notNullValue());
        assertThat(returnedQuestion.getText(), is(question.getText()));
        assertThat(returnedQuestion.getPosition(), is(question.getPosition()));
    }

    @Test
    public void testCreateQuestion__Service_Service() throws Exception {

        Question question = new Question().setText("What is REST?").setPosition(1);

        Answer answer = new Answer().setText("REpresentational State Transfer");
        question.getAnswers().add(answer);


        Long id = interviewService.saveOrUpdateQuestion(question).getId();
        Question returnedQuestion = interviewService.findQuestionById(id);

        assertThat(returnedQuestion.getId(), notNullValue());
        assertThat(returnedQuestion.getText(), is(question.getText()));
        assertThat(returnedQuestion.getPosition(), is(question.getPosition()));
    }



}