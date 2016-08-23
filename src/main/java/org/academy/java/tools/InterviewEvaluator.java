package org.academy.java.tools;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import static org.academy.java.entity.Question.QuestionType.CHECKBOX;
import static org.academy.java.entity.Question.QuestionType.RADIO;
import static org.academy.java.entity.Question.QuestionType.TEXT_AREA;

@Component
public class InterviewEvaluator {

    private int evaluateTextQuestion(Question question) {
        throw new NotImplementedException("");
        /*
        if (question.getAnswers().iterator().next().isCorrect()) {
            return 1;
        }
        return 0;
        */
    }

    private int evaluateCheckboxQuestion(Question question) {
        throw new NotImplementedException("");
        /*
        for (Answer answer : question.getAnswers()) {
            if ((answer.isCorrect() && !answer.isChosen()) || (!answer.isCorrect() && answer.isChosen())) {
                return 0;
            }
        }
        return 1;
        */
    }

    private int evaluateRadioQuestion(Question question) {
        throw new NotImplementedException("");
        /*
        for (Answer answer : question.getAnswers()) {
            if ((answer.isCorrect() && answer.isChosen())) {
                return 1;
            }
        }
        return 0;
        */
    }

    public int evaluateInterview(Interview interview) {
        throw new NotImplementedException("");
        /*
        int result = 0;
        for (Question question : interview.getQuestions()) {
            if (question.getQuestionType() == TEXT_AREA) {
                result += evaluateTextQuestion(question);
                continue;
            }
            if (question.getQuestionType() == CHECKBOX) {
                result += evaluateCheckboxQuestion(question);
                continue;
            }
            if (question.getQuestionType() == RADIO) {
                result += evaluateRadioQuestion(question);
                continue;
            }
        }
        return result;
        */
    }
}
