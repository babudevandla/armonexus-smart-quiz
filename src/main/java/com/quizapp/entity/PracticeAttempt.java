package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "practice_attempts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PracticeAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "practice_set_id", nullable = false)
    private PracticeSet practiceSet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double scoreObtained;
    private LocalDateTime attemptedAt;

    @PrePersist
    protected void onCreate() {
        attemptedAt = LocalDateTime.now();
    }
}
