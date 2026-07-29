package com.quizapp.config;

import com.quizapp.entity.DifficultyLevel;
import com.quizapp.entity.QuestionType;
import com.quizapp.entity.Role;
import com.quizapp.entity.User;
import com.quizapp.repository.DifficultyLevelRepository;
import com.quizapp.repository.QuestionTypeRepository;
import com.quizapp.repository.RoleRepository;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Seeds the database with default roles, an initial admin user, and
 * common master data (question types, difficulty levels) so the
 * application is usable immediately after first startup.
 *
 * Default login: admin@quizapp.com / Admin@123
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("ROLE_ADMIN").description("Full system access").build()));

        roleRepository.findByName("ROLE_REVIEWER")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("ROLE_REVIEWER").description("Reviews and approves questions").build()));

        roleRepository.findByName("ROLE_INSTRUCTOR")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("ROLE_INSTRUCTOR").description("Creates quizzes and questions").build()));

        roleRepository.findByName("ROLE_CANDIDATE")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("ROLE_CANDIDATE").description("Takes quizzes").build()));

        if (!userRepository.existsByEmail("admin@quizapp.com")) {
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            User admin = User.builder()
                    .fullName("System Administrator")
                    .email("admin@quizapp.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .enabled(true)
                    .emailVerified(true)
                    .roles(roles)
                    .build();
            userRepository.save(admin);
        }

        Role reviewerRole = roleRepository.findByName("ROLE_REVIEWER").orElseThrow();
        Role instructorRole = roleRepository.findByName("ROLE_INSTRUCTOR").orElseThrow();
        Role candidateRole = roleRepository.findByName("ROLE_CANDIDATE").orElseThrow();

        if (!userRepository.existsByEmail("instructor@quizapp.com")) {
            Set<Role> roles = new HashSet<>();
            roles.add(instructorRole);
            userRepository.save(User.builder()
                    .fullName("Sample Instructor")
                    .email("instructor@quizapp.com")
                    .password(passwordEncoder.encode("Instructor@123"))
                    .enabled(true).emailVerified(true).roles(roles).build());
        }

        if (!userRepository.existsByEmail("reviewer@quizapp.com")) {
            Set<Role> roles = new HashSet<>();
            roles.add(reviewerRole);
            userRepository.save(User.builder()
                    .fullName("Sample Reviewer")
                    .email("reviewer@quizapp.com")
                    .password(passwordEncoder.encode("Reviewer@123"))
                    .enabled(true).emailVerified(true).roles(roles).build());
        }

        if (!userRepository.existsByEmail("candidate@quizapp.com")) {
            Set<Role> roles = new HashSet<>();
            roles.add(candidateRole);
            userRepository.save(User.builder()
                    .fullName("Sample Candidate")
                    .email("candidate@quizapp.com")
                    .password(passwordEncoder.encode("Candidate@123"))
                    .enabled(true).emailVerified(true).roles(roles).build());
        }

        if (questionTypeRepository.count() == 0) {
            questionTypeRepository.save(QuestionType.builder().name("SINGLE_CHOICE").build());
            questionTypeRepository.save(QuestionType.builder().name("MULTIPLE_CHOICE").build());
            questionTypeRepository.save(QuestionType.builder().name("TRUE_FALSE").build());
            questionTypeRepository.save(QuestionType.builder().name("FILL_IN_THE_BLANK").build());
            questionTypeRepository.save(QuestionType.builder().name("DESCRIPTIVE").build());
        }

        if (difficultyLevelRepository.count() == 0) {
            difficultyLevelRepository.save(DifficultyLevel.builder().name("Easy").weight(1).build());
            difficultyLevelRepository.save(DifficultyLevel.builder().name("Medium").weight(2).build());
            difficultyLevelRepository.save(DifficultyLevel.builder().name("Hard").weight(3).build());
        }
    }
}
