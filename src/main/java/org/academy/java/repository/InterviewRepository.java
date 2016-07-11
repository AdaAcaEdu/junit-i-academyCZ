package org.academy.java.repository;


import org.academy.java.entity.Interview;
import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

import static org.academy.java.entity.Interview.*;

public interface InterviewRepository extends PagingAndSortingRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.state = :state)")
    Set<Interview> find(@Param(State.class) State state);

    Set<Interview> findByState(@Param(State.class) State state);
}
