package com.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "application_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationSettings {

    @Id
    private Long id; // always 1 - singleton settings row

    @Builder.Default
    private String siteName = "Quiz Admin Portal";

    @Builder.Default
    private String siteLogoUrl = "";

    @Builder.Default
    private String timezone = "UTC";

    @Builder.Default
    private String dateFormat = "dd-MM-yyyy";

    @Builder.Default
    private Integer itemsPerPage = 20;

    @Builder.Default
    private Boolean maintenanceMode = false;
}
