package com.quizapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // allows @PreAuthorize on controller methods
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/resources/**","/**", "/login", "/error", "/css/**", "/js/**").permitAll()

                // Admin-only areas
                .requestMatchers("/users/**", "/roles/**", "/user-groups/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/settings/**", "/audit-logs/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/subjects/**", "/categories/**", "/difficulty-levels/**",
                                  "/question-types/**", "/tags/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/quiz-schedules/**", "/quiz-assignments/**", "/invitations/**")
                    .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/notifications/**", "/reports/**", "/export-reports/**")
                    .hasAuthority("ROLE_ADMIN")
                .requestMatchers("/results/**", "/live-monitoring/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/practice-sets/**", "/practice-history/**").hasAuthority("ROLE_ADMIN")

                // Reviewer (and Admin) approve questions
                .requestMatchers("/question-approval/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_REVIEWER")
                .requestMatchers("/reviewer/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_REVIEWER")

                // Instructor (and Admin) build content
                .requestMatchers("/instructor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_INSTRUCTOR")
                .requestMatchers("/questions/**", "/quizzes/**", "/question-attachments/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_INSTRUCTOR")

                // Candidate-only area
                .requestMatchers("/candidate/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CANDIDATE")

                // Shared across all authenticated roles
                .requestMatchers("/leaderboard", "/certificates/**", "/profile", "/dashboard", "/").authenticated()

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }
}
