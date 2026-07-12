package com.quizapp.service;

import com.quizapp.entity.Quiz;

import java.util.List;

public interface QuizService {
    List<Quiz> findAll();
    Quiz findById(Long id);
    Quiz save(Quiz quiz);
    void deleteById(Long id);
}
