package com.quizapp.service;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionStatus;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();
    List<Question> findByStatus(QuestionStatus status);
    Question findById(Long id);
    Question save(Question question);
    void deleteById(Long id);
    void approve(Long id, Long reviewerId, String comments);
    void reject(Long id, Long reviewerId, String comments);
}
