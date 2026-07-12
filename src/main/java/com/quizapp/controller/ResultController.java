package com.quizapp.controller;

import com.quizapp.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final QuizResultRepository quizResultRepository;

    @GetMapping
    public String list(Model model) {
        // only completed attempts — in-progress ones (submittedAt == null) show on Live Monitoring instead
        model.addAttribute("results", quizResultRepository.findBySubmittedAtIsNotNull());
        return "results/list";
    }
}
