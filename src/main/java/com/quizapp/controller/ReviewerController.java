package com.quizapp.controller;

import com.quizapp.entity.QuestionStatus;
import com.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviewer")
@RequiredArgsConstructor
public class ReviewerController {

    private final QuestionRepository questionRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingCount", questionRepository.findByStatus(QuestionStatus.PENDING_REVIEW).size());
        model.addAttribute("approvedCount", questionRepository.findByStatus(QuestionStatus.APPROVED).size());
        model.addAttribute("rejectedCount", questionRepository.findByStatus(QuestionStatus.REJECTED).size());
        return "reviewer/dashboard";
    }
}
