package com.quizapp.service;

import com.quizapp.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();
    Subject findById(Long id);
    Subject save(Subject subject);
    void deleteById(Long id);
}
