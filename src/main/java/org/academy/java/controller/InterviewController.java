package org.academy.java.controller;

import org.academy.java.entity.Interview;
import org.academy.java.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @RequestMapping(value = "/interviews")
    Iterable<Interview> getInterviews() {
        return interviewService.getInterviews();
    }
}
