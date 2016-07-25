package org.academy.java.controller;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.service.InterviewService;
import org.academy.java.service.QuestionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private InterviewService interviewService;


    @RequestMapping(value = "/questions", method = POST)
    Question saveOrUpdateQuestion(@RequestBody Question question) {
        return questionService.saveOrUpdateQuestion(question);
    }

    @RequestMapping(value = "/questions")
    Iterable<Question> saveOrUpdateQuestion() {
        return questionService.findAllQuestions();
    }

    @RequestMapping(value = "/question/{id}", method = GET)
    Question getQuestionById(@PathVariable("id") Long id) {
        return questionService.findQuestionById(id);
    }

    @RequestMapping(value = "/question/{id}", method = DELETE)
    void deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
    }

    @RequestMapping(value = "/question/{id}/answers", method = POST)
    Question addAnswer(@PathVariable("id") Long questionId, @RequestBody Answer answer) {

        Question question = questionService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);
        return questionService.addAnswer(question, answer);
    }


    @RequestMapping(value = "/textAnswer", method = POST)
    Question makeTextAnswerForQuestion(@PathVariable("id") Long questionId) {

        Question question = questionService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);
        return questionService.makeTextAnswerForQuestion(question);
    }

    @RequestMapping(value = "/radioAnswer", method = POST)
    Question makeRadioAnswersForQuestion(@PathVariable("id") Long questionId, @RequestParam(required = false) Long chosenAnswerId) {

        Question question = questionService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);

        if (chosenAnswerId != null) {
            Answer answer = questionService.findAnswerById(chosenAnswerId);
            throwNotFoundExceptionOnNull(answer, Answer.class, chosenAnswerId);

            if(question.getAnswers().contains(answer)){
                // TODO log.error();
                //TODO throw DoewNotBelongTOQuestion
            }
        }

        return questionService.makeRadioAnswersForQuestion(question, chosenAnswerId);
    }

    @RequestMapping(value = "/CheckboxAnswer", method = POST)
    Question makeCheckboxAnswersForQuestion(@PathVariable("id") Long questionId) {

        Question question = questionService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);

        return questionService.makeCheckboxAnswerForQuestion(question);
    }


    private void throwNotFoundExceptionOnNull(Object o, Class clazz, Long id) {

        if (o == null) {
            log.error("Could not find " + clazz.getCanonicalName() + " with id: '" + id + "'.");
            //throw not found ex
        }
    }

    private final static transient Logger log = getLogger(QuestionController.class);
}
