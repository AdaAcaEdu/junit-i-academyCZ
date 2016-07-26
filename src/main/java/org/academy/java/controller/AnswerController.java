package org.academy.java.controller;

import org.academy.java.entity.Answer;
import org.academy.java.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE)
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/answer/{id}/changeCorrectness", method = RequestMethod.PUT)
    public Answer changeCorrectness(@PathVariable Long id) {
        return answerService.changeCorrectness(id);
    }
}
