package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "security_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SecuritySettings {

    @Id
    private Long id; // always 1

    @Builder.Default
    private Integer passwordMinLength = 8;

    @Builder.Default
    private Integer sessionTimeoutMinutes = 30;

    @Builder.Default
    private Integer maxLoginAttempts = 5;

    @Builder.Default
    private Boolean enableTwoFactor = false;
}
