package org.academy.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.academy.java.Application;
import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class QuestionControllerTest {

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testMakeTextAnswerForQuestion() throws Exception {

        Question question = new Question().setText("What is REST?").setPosition(1);

        Answer answer = new Answer().setText("REpresentational State Transfer");
        question.getAnswers().add(answer);

        // post
        MvcResult mvcResult = mockMvc.perform(post("/questions")
                .content(toJson(question))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        Question returnedQuestion = toObject(mvcResult.getResponse().getContentAsString(), Question.class);
        System.out.println(returnedQuestion);
    }

    @Test
    public void testMakeRadioAnswersForQuestion() throws Exception {

    }

    @Test
    public void testMakeCheckboxAnswersForQuestion() throws Exception {

    }

//    private String toJson(Object o) throws IOException {
//        return new ObjectMapper().writeValueAsString(o);
//    }

    protected String toJson(final Object o) throws IOException {

        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected <T> T toObject(String json, final Class<T> clazz) throws IOException {

        MockHttpInputMessage mockHttpInputMessage = new MockHttpInputMessage(json.getBytes());
        return (T) this.mappingJackson2HttpMessageConverter.read(clazz, mockHttpInputMessage);

    }

}