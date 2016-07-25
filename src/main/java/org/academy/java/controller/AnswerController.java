package org.academy.java.controller;

import org.academy.java.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE)
    void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }
}
