package com.quizapp.repository;

import com.quizapp.entity.QuizSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizScheduleRepository extends JpaRepository<QuizSchedule, Long> {
    List<QuizSchedule> findByQuizId(Long quizId);
}
