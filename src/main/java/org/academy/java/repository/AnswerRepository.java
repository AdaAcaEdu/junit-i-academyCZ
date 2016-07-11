package org.academy.java.repository;


import org.academy.java.entity.Answer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Answer a WHERE a.question.id = :questionId)")
    void deleteAllByQuestionId(@Param("questionId") Long questionId);
}
