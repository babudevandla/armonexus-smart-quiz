package com.quizapp.repository;

import com.quizapp.entity.PaymentGatewaySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGatewaySettingsRepository extends JpaRepository<PaymentGatewaySettings, Long> {
}
