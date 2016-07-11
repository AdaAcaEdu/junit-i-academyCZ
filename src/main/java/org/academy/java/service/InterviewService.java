package org.academy.java.service;


import org.academy.java.entity.Answer;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.academy.java.entity.Question.QuestionType.*;

@Service
public class InterviewService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Transactional
    public Question makeCheckboxAnswerForQuestion(Question question) {

        question.setQuestionType(CHECKBOX);

        return questionRepository.save(question);
    }

    @Transactional
    public Question makeTextAnswerForQuestion(Question question) {

        answerRepository.deleteAllByQuestionId(question.getId());
        Answer a = new Answer();
        a.setQuestion(question);
        a.setText("");
        answerRepository.save(a);

        question.setQuestionType(TEXT_AREA);

        return questionRepository.save(question);
    }

    @Transactional
    public Question makeRadioAnswersForQuestion(Question question, Long chosenAnswerId) {

        question.setQuestionType(RADIO);

        if (question.getAnswers().size() == 0) {

            question.getAnswers().add(
                    new Answer().setRight(true).setQuestion(question)
            );
            return questionRepository.save(question);
        }

        Long idOfChosenRadioAnswer = chosenAnswerId != null
                ? chosenAnswerId
                : question.getAnswers().stream().filter(answer -> answer.isRight()).findFirst()
                        .orElse(question.getAnswers().stream().findFirst().get())
                        .getId();

        question.getAnswers().stream().forEach(
                a -> {
                    a.setRight(a.getId() == idOfChosenRadioAnswer);
                }
        );

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long questionId) {
        return questionRepository.findOne(questionId);
    }

    @Transactional(readOnly = true)
    public Answer findAnswerById(Long answerId) {
        return answerRepository.findOne(answerId);
    }
}
