package com.quizapp.controller;

import com.quizapp.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LiveMonitoringController {

    private final QuizResultRepository quizResultRepository;

    @GetMapping("/live-monitoring")
    public String liveMonitoring(Model model) {
        // "in progress" = an attempt that has started (a row was written when
        // the candidate opened the quiz) but not yet submitted
        model.addAttribute("inProgressAttempts", quizResultRepository.findBySubmittedAtIsNull());
        return "live-monitoring";
    }
}
