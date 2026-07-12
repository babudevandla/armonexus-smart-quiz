package com.quizapp.service.impl;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionOption;
import com.quizapp.entity.QuestionStatus;
import com.quizapp.entity.User;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findByStatus(QuestionStatus status) {
        return questionRepository.findByStatus(status);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id " + id));
    }

    @Override
    @Transactional
    public Question save(Question question) {
        // link back-reference so cascade persists options correctly
        if (question.getOptions() != null) {
            for (QuestionOption opt : question.getOptions()) {
                opt.setQuestion(question);
            }
        }
        if (question.getStatus() == null) {
            question.setStatus(QuestionStatus.PENDING_REVIEW);
        }
        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void approve(Long id, Long reviewerId, String comments) {
        Question q = findById(id);
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new EntityNotFoundException("Reviewer not found"));
        q.setStatus(QuestionStatus.APPROVED);
        q.setReviewedBy(reviewer);
        q.setReviewComments(comments);
        questionRepository.save(q);
    }

    @Override
    @Transactional
    public void reject(Long id, Long reviewerId, String comments) {
        Question q = findById(id);
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new EntityNotFoundException("Reviewer not found"));
        q.setStatus(QuestionStatus.REJECTED);
        q.setReviewedBy(reviewer);
        q.setReviewComments(comments);
        questionRepository.save(q);
    }
}
