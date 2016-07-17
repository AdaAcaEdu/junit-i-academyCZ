package org.academy.java.service;

import org.academy.java.entity.Interview;
import org.academy.java.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    @Autowired
    InterviewRepository interviewRepository;


    public Iterable<Interview> getInterviews(){
        return interviewRepository.findAll();
    }
}
