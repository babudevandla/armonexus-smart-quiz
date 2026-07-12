package com.quizapp.controller;

import com.quizapp.entity.QuestionStatus;
import com.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository quizResultRepository;

    @GetMapping("/users")
    public String userReports(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("users", userRepository.findAll());
        return "reports/users";
    }

    @GetMapping("/quizzes")
    public String quizReports(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        // completed attempts only
        model.addAttribute("allResults", quizResultRepository.findBySubmittedAtIsNotNull());
        return "reports/quizzes";
    }

    @GetMapping("/questions")
    public String questionReports(Model model) {
        model.addAttribute("totalQuestions", questionRepository.count());
        model.addAttribute("approved", questionRepository.findByStatus(QuestionStatus.APPROVED).size());
        model.addAttribute("pending", questionRepository.findByStatus(QuestionStatus.PENDING_REVIEW).size());
        model.addAttribute("rejected", questionRepository.findByStatus(QuestionStatus.REJECTED).size());
        return "reports/questions";
    }

    @GetMapping("/performance")
    public String performanceAnalytics(Model model) {
        // completed attempts only — an in-progress attempt shouldn't count against the pass rate
        long totalAttempts = quizResultRepository.findBySubmittedAtIsNotNull().size();
        long passedCount = quizResultRepository.findBySubmittedAtIsNotNull().stream()
                .filter(r -> Boolean.TRUE.equals(r.getPassed())).count();
        double passRate = totalAttempts == 0 ? 0 : (passedCount * 100.0 / totalAttempts);
        model.addAttribute("totalAttempts", totalAttempts);
        model.addAttribute("passedCount", passedCount);
        model.addAttribute("passRate", passRate);
        return "reports/performance";
    }

    @GetMapping("/revenue")
    public String revenueReports(Model model) {
        // Placeholder: no payment/subscription entity is modeled yet in this reference build.
        return "reports/revenue";
    }
}
