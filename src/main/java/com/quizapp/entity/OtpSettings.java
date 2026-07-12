package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "otp_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OtpSettings {

    @Id
    private Long id; // always 1

    @Builder.Default
    private Integer otpLength = 6;

    @Builder.Default
    private Integer otpExpiryMinutes = 5;

    @Builder.Default
    private String otpProvider = "TWILIO";

    @Builder.Default
    private Boolean enabled = true;
}
