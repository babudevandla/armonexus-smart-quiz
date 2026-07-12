package com.quizapp.controller;

import com.quizapp.entity.QuestionStatus;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question-approval")
@RequiredArgsConstructor
public class QuestionApprovalController {

    private final QuestionService questionService;
    private final UserRepository userRepository;

    @GetMapping
    public String pending(Model model) {
        model.addAttribute("questions", questionService.findByStatus(QuestionStatus.PENDING_REVIEW));
        return "questions/review";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id,
                           @RequestParam(required = false) String comments,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        Long reviewerId = userRepository.findByEmail(authentication.getName()).orElseThrow().getId();
        questionService.approve(id, reviewerId, comments);
        redirectAttributes.addFlashAttribute("successMessage", "Question approved.");
        return "redirect:/question-approval";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id,
                          @RequestParam(required = false) String comments,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        Long reviewerId = userRepository.findByEmail(authentication.getName()).orElseThrow().getId();
        questionService.reject(id, reviewerId, comments);
        redirectAttributes.addFlashAttribute("successMessage", "Question rejected.");
        return "redirect:/question-approval";
    }
}
