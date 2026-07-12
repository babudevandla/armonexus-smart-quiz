package com.quizapp.controller;

import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizRepository;
import com.quizapp.repository.QuizResultRepository;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;

    /**
     * Central landing route after login. Routes each role to its own
     * dashboard so every role gets a tailored home page.
     */
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Authentication authentication, Model model) {
        boolean isAdmin = false;
        for (GrantedAuthority a : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(a.getAuthority())) { isAdmin = true; break; }
            if ("ROLE_INSTRUCTOR".equals(a.getAuthority())) return "redirect:/instructor/dashboard";
            if ("ROLE_REVIEWER".equals(a.getAuthority())) return "redirect:/reviewer/dashboard";
            if ("ROLE_STUDENT".equals(a.getAuthority())) return "redirect:/student/dashboard";
        }

        if (isAdmin) {
            model.addAttribute("totalUsers", userRepository.count());
            model.addAttribute("totalQuestions", questionRepository.count());
            model.addAttribute("totalQuizzes", quizRepository.count());
            // "completed" attempts only, so someone mid-exam doesn't inflate this count
            model.addAttribute("totalAttempts", quizResultRepository.findBySubmittedAtIsNotNull().size());
            return "dashboard";
        }

        // fallback for a user with no recognized role
        return "redirect:/profile";
    }
}
