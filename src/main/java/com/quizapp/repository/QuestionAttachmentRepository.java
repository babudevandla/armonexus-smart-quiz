package com.quizapp.repository;

import com.quizapp.entity.QuestionAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAttachmentRepository extends JpaRepository<QuestionAttachment, Long> {
}
