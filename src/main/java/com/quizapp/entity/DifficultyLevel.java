package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "difficulty_levels")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // Easy, Medium, Hard

    @Column(nullable = false)
    private Integer weight; // used for scoring/negative marking multipliers
}
