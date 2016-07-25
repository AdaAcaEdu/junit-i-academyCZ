package org.academy.java.service;

import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.academy.java.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Transactional
    public Interview saveOrUpdateInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    public Iterable<Interview> getInterviews(){
        return interviewRepository.findAll();
    }

    public Interview getInterview(Long id) {
        return interviewRepository.findOne(id);
    }
}
