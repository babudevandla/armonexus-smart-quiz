package com.quizapp.service.impl;

import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuizQuestion;
import com.quizapp.repository.QuizRepository;
import com.quizapp.service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz findById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id " + id));
    }

    @Override
    @Transactional
    public Quiz save(Quiz quiz) {
        if (quiz.getQuizQuestions() != null) {
            for (QuizQuestion qq : quiz.getQuizQuestions()) {
                qq.setQuiz(quiz);
            }
        }
        return quizRepository.save(quiz);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
