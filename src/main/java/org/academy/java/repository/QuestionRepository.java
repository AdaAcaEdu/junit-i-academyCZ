package org.academy.java.repository;


import org.academy.java.entity.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

}
