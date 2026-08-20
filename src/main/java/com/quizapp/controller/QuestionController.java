package com.quizapp.controller;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionOption;
import com.quizapp.entity.QuestionStatus;
import com.quizapp.entity.User;
import com.quizapp.repository.CategoryRepository;
import com.quizapp.repository.DifficultyLevelRepository;
import com.quizapp.repository.QuestionTypeRepository;
import com.quizapp.repository.SubjectRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.info("Fetching all questions");
        model.addAttribute("questions", questionService.findAll());
        log.info("Questions added to model");
        return "questions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new question");
        Question question = new Question();
        // start with 4 blank options by default (typical MCQ)
        List<QuestionOption> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            options.add(new QuestionOption());
        }
        question.setOptions(options);

        model.addAttribute("question", question);
        addReferenceData(model);
        log.info("New question object with default options added to model");
        return "questions/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing question with ID: {}", id);
        model.addAttribute("question", questionService.findById(id));
        addReferenceData(model);
        log.info("Question object added to model for editing");
        return "questions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Question question,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        log.info("Saving question: {}", question);

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
        log.info("Question saved successfully: {}", question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete question with ID: {}", id);

        try {
            questionService.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Question deleted successfully."
            );
        } catch (IllegalStateException e) {
            log.error("Failed to delete question: {}", e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        } catch (DataIntegrityViolationException e) {
            log.error("Cannot delete question because it has attachments or is referenced: {}", e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This question cannot be deleted because it has attached files . Please remove attachments first."
            );
        }

        log.info("Question deletion attempt completed for ID: {}", id);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/review")
    public String reviewQuestion(@PathVariable Long id, Model model) {
        log.info("Fetching question for review with ID: {}", id);
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        log.info("Question for review added to model: {}", question);
        return "questions/review-detail";
    }

    private void addReferenceData(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("difficultyLevels", difficultyLevelRepository.findAll());
        model.addAttribute("questionTypes", questionTypeRepository.findAll());
    }
}

