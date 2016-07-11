package org.academy.java.controller;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.academy.java.service.InterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/questions/{id}")
public class QuestionController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private InterviewService interviewService;


    @RequestMapping(value = "/textAnswer", method = POST)
    Question makeTextAnswerForQuestion(@PathVariable("id") Long questionId) {

        Question question = interviewService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);
        return interviewService.makeTextAnswerForQuestion(question);
    }

    @RequestMapping(value = "/radioAnswer", method = POST)
    Question makeRadioAnswersForQuestion(@PathVariable("id") Long questionId, @RequestParam(required = false) Long chosenAnswerId) {

        Question question = interviewService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);

        if (chosenAnswerId != null) {
            Answer answer = interviewService.findAnswerById(chosenAnswerId);
            throwNotFoundExceptionOnNull(answer, Answer.class, chosenAnswerId);

            if(question.getAnswers().contains(answer)){
                // TODO log.error();
                //TODO throw DoewNotBelongTOQuestion
            }
        }

        return interviewService.makeRadioAnswersForQuestion(question, chosenAnswerId);
    }

    @RequestMapping(value = "/CheckboxAnswer", method = POST)
    Question makeCheckboxAnswersForQuestion(@PathVariable("id") Long questionId) {

        Question question = interviewService.findQuestionById(questionId);
        throwNotFoundExceptionOnNull(question, Question.class, questionId);

        return interviewService.makeCheckboxAnswerForQuestion(question);
    }


    private void throwNotFoundExceptionOnNull(Object o, Class clazz, Long id) {

        if (o == null) {
            log.error("Could not find " + clazz.getCanonicalName() + " with id: '" + id + "'.");
            //throw not found ex
        }
    }

    private final static transient Logger log = LoggerFactory.getLogger(QuestionController.class);
}
