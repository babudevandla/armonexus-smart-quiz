package com.quizapp.repository;

import com.quizapp.entity.SmsNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsNotificationRepository extends JpaRepository<SmsNotification, Long> {
}
