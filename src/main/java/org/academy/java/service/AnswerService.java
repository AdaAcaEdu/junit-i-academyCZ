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

    public void deleteAnswer(Long id) {
        answerRepository.delete(id);
    }

    public Answer changeCorrectness(Long id) {
        Answer answer = answerRepository.findOne(id);
        Question question = answer.getQuestion();
        if (question.getQuestionType() == Question.QuestionType.RADIO) {
            for (Answer a : question.getAnswers()) {
                if (a.getId() != id) {
                    a.setCorrect(false);
                    answerRepository.save(a);
                }
            }
        }
        answer.setCorrect(!answer.isCorrect());
        answerRepository.save(answer);
        return answer;
    }
}
