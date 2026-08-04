package com.quizapp.service.impl;

import com.quizapp.entity.Subject;
import com.quizapp.repository.SubjectRepository;
import com.quizapp.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        log.info("Fetching all subjects");
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        log.info("Finding subject by ID: {}", id);
        return subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));
    }

    @Override
    public Subject save(Subject subject) {
        log.info("Saving subject: {}", subject);

        if (subject.getId() == null) {
            if (subjectRepository.existsByNameIgnoreCase(subject.getName().trim())) {
                throw new IllegalArgumentException("Subject already exists.");
            }
        } else {
            if (subjectRepository.existsByNameIgnoreCaseAndIdNot(
                    subject.getName().trim(), subject.getId())) {
                throw new IllegalArgumentException("Subject already exists.");
            }
        }

        return subjectRepository.save(subject);
    }

    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

}
