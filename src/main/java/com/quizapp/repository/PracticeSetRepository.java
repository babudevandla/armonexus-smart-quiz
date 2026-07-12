package com.quizapp.repository;

import com.quizapp.entity.PracticeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeSetRepository extends JpaRepository<PracticeSet, Long> {
}
