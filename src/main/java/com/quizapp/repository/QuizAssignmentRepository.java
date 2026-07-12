package com.quizapp.repository;

import com.quizapp.entity.QuizAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAssignmentRepository extends JpaRepository<QuizAssignment, Long> {
    List<QuizAssignment> findByUserId(Long userId);
    List<QuizAssignment> findByGroupIdIn(List<Long> groupIds);
}
