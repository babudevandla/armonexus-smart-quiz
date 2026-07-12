package com.quizapp.controller;

import com.quizapp.entity.QuizResult;
import com.quizapp.entity.User;
import com.quizapp.repository.QuizResultRepository;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeaderboardController {

    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        // only completed attempts, ranked by score
        List<QuizResult> ranked = quizResultRepository.findBySubmittedAtIsNotNull().stream()
                .sorted(Comparator.comparing(QuizResult::getScoreObtained).reversed())
                .toList();
        model.addAttribute("results", ranked);
        return "leaderboard";
    }

    @GetMapping("/certificates")
    public String certificates(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        List<QuizResult> passedResults;
        if (isAdmin) {
            // Admin sees every passed attempt across all students
            passedResults = quizResultRepository.findBySubmittedAtIsNotNull().stream()
                    .filter(QuizResult::getPassed)
                    .toList();
        } else {
            // Everyone else (Student) only sees their own passed attempts
            User me = userRepository.findByEmail(authentication.getName()).orElseThrow();
            passedResults = quizResultRepository.findByUserIdAndSubmittedAtIsNotNull(me.getId()).stream()
                    .filter(QuizResult::getPassed)
                    .toList();
        }

        model.addAttribute("results", passedResults);
        model.addAttribute("isAdmin", isAdmin);
        return "certificates";
    }
}
