package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_gateway_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentGatewaySettings {

    @Id
    private Long id; // always 1

    @Builder.Default
    private String provider = "RAZORPAY";

    private String apiKey;
    private String apiSecret;

    @Builder.Default
    private String currency = "USD";

    @Builder.Default
    private Boolean testMode = true;
}
