package org.academy.java.controller;

import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.academy.java.service.InterviewService;
import org.academy.java.service.QuestionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api")
public class InterviewController {

    private final static transient Logger log = getLogger(QuestionController.class);

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private QuestionService questionService;


    @RequestMapping(value = "/interviews")
    Iterable<Interview> getInterviews() {
        return interviewService.getInterviews();
    }

    @RequestMapping(value = "/interviews/{id}")
    Interview getInterview(@PathVariable Long id) {
        return interviewService.getInterview(id);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/interviews/{id}/questions", method = POST)
    public Interview addQuestion (@RequestBody Question question, @PathVariable("id") Long interviewId) {
        Interview interview = interviewService.getInterview(interviewId);
        throwNotFoundExceptionOnNull(interview, Interview.class, interviewId);

        interview.getQuestions().add(question);
        question.setInterview(interview);
        questionService.saveOrUpdateQuestion(question);

        return interviewService.saveOrUpdateInterview(interview);
    }

    private void throwNotFoundExceptionOnNull(Object o, Class clazz, Long id) {

        if (o == null) {
            log.error("Could not find " + clazz.getCanonicalName() + " with id: '" + id + "'.");
            //throw not found ex
        }
    }
}
