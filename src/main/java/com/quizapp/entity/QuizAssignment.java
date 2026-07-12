package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_assignments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuizAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // nullable if assigned via group

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup group; // nullable if assigned via user
}
