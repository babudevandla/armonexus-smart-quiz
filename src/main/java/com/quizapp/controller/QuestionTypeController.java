package com.quizapp.controller;

import com.quizapp.entity.QuestionType;
import com.quizapp.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequestMapping("/question-types")
@RequiredArgsConstructor
public class QuestionTypeController {

    private final QuestionTypeRepository questionTypeRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all question types");
        model.addAttribute("types", questionTypeRepository.findAll());
        log.info("Question types added to model");
        return "questiontypes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new question type");
        model.addAttribute("type", new QuestionType());
        log.info("New question type object added to model");
        return "questiontypes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing question type with ID: {}", id);
        model.addAttribute("type", questionTypeRepository.findById(id).orElseThrow());
        log.info("Question type object added to model");
        return "questiontypes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("type") QuestionType type,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        log.info("Saving question type: {}", type);

        try {
            questionTypeRepository.save(type);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Question type saved successfully."
            );
            log.info("Question type saved successfully: {}", type);
            return "redirect:/question-types";
        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate question type name: {}", type.getName(), e);
            model.addAttribute("type", type);
            model.addAttribute("errorMessage", 
                    "A question type with this name already exists. Please use a different name.");
            return "questiontypes/form";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Deleting question type with ID: {}", id);
        questionTypeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Question type deleted successfully.");
        log.info("Question type with ID: {} deleted successfully", id);
        return "redirect:/question-types";
    }
}
