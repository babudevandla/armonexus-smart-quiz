package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.*;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final SubjectRepository subjectRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "questions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Question question = new Question();
        // start with 4 blank options by default (typical MCQ)
        List<QuestionOption> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            options.add(new QuestionOption());
        }
        question.setOptions(options);

        model.addAttribute("question", question);
        addReferenceData(model);
        return "questions/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        addReferenceData(model);
        return "questions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Question question,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes) {

        // remove blank options the user did not fill in
        if (question.getOptions() != null) {
            question.getOptions().removeIf(o -> o.getOptionText() == null || o.getOptionText().isBlank());
        }

        if (question.getId() == null) {
            User currentUser = userRepository.findByEmail(authentication.getName()).orElse(null);
            question.setCreatedBy(currentUser);
            question.setStatus(QuestionStatus.PENDING_REVIEW);
        }

        questionService.save(question);
        redirectAttributes.addFlashAttribute("successMessage", "Question saved and sent for review.");
        return "redirect:/questions";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        questionService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Question deleted successfully.");
        return "redirect:/questions";
    }

    private void addReferenceData(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("difficultyLevels", difficultyLevelRepository.findAll());
        model.addAttribute("questionTypes", questionTypeRepository.findAll());
    }
}
