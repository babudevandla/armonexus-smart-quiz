package com.quizapp.repository;

import com.quizapp.entity.OtpSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpSettingsRepository extends JpaRepository<OtpSettings, Long> {
}
