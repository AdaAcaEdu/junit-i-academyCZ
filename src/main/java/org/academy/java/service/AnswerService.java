package org.academy.java.service;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findOne(id);
        Question question = answer.getQuestion();
        question.getAnswers().remove(answer);
        questionRepository.save(question);
        answerRepository.delete(answer.getId());
    }
}
