package com.quizapp.repository;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByStatus(QuestionStatus status);
    List<Question> findBySubjectId(Long subjectId);
    List<Question> findByCreatedById(Long userId);
}
