package org.academy.java.tools

import org.academy.java.entity.Answer;
import org.academy.java.entity.Question
import spock.lang.Specification;

public class InterviewEvaluatorTest extends Specification{

    def "Evaluate checkbox question"() {

        setup:
        InterviewEvaluator interviewEvaluator = new InterviewEvaluator();
        Question question = new Question();
        question.setQuestionType(Question.QuestionType.CHECKBOX);
        Answer a1 = new Answer().setId(1);
        question.getAnswers().add(a1);
        Answer a2 = new Answer().setId(2);
        question.getAnswers().add(a2);

        when:
        a1.setChosen(a1Chosen).setCorrect(a1Correct);
        a2.setChosen(a2Chosen).setCorrect(a2Correct);

        then:
        interviewEvaluator.evaluateCheckboxQuestion(question) == evaluation;

        where:
        a1Chosen | a1Correct | a2Chosen | a2Correct | evaluation
        false    | false     | false    | false     | 1
        true     | true      | false    | false     | 1
        true     | true      | true     | true      | 1
        true     | true      | true     | false     | 0
        false    | true      | true     | true      | 0
    }

}