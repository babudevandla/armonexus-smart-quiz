package com.quizapp.repository;

import com.quizapp.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByQuizId(Long quizId);
    List<QuizResult> findByUserId(Long userId);

    // "completed" results only — excludes in-progress attempts (submittedAt is null)
    List<QuizResult> findBySubmittedAtIsNotNull();
    List<QuizResult> findByUserIdAndSubmittedAtIsNotNull(Long userId);

    // used to find (and resume/update) an attempt that has started but not yet been submitted
    Optional<QuizResult> findFirstByQuizIdAndUserIdAndSubmittedAtIsNull(Long quizId, Long userId);

    List<QuizResult> findBySubmittedAtIsNull();
}
