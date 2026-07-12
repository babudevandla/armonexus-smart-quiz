package com.quizapp.repository;

import com.quizapp.entity.ApplicationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationSettingsRepository extends JpaRepository<ApplicationSettings, Long> {
}
