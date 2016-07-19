package org.academy.java.repository;


import org.academy.java.entity.Question;
import org.academy.java.entity.Question.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public interface QuestionRepository extends QuestionRepositoryCustom, PagingAndSortingRepository<Question, Long> {
}

interface QuestionRepositoryCustom {

    List<Question> getByQuestionTypes(QuestionType... types);
}

class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private EntityManager em;

    @Autowired
    public QuestionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Question> getByQuestionTypes(QuestionType... types) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);

        List<Predicate> predicates = new ArrayList<>();
        Root<Question> fromQuestion = cq.from(Question.class);

        for (QuestionType type : types) {
            predicates.add(cb.equal(fromQuestion.get("questionType"), type));
        }

        cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        return em.createQuery(cq).getResultList();
    }
}