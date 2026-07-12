package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_configuration")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmailConfiguration {

    @Id
    private Long id; // always 1

    private String smtpHost;

    @Builder.Default
    private Integer smtpPort = 587;

    private String smtpUsername;
    private String smtpPassword;

    @Builder.Default
    private String fromAddress = "no-reply@quizapp.com";

    @Builder.Default
    private Boolean useTls = true;
}
