package com.quizapp.controller;

import com.quizapp.entity.Subject;
import com.quizapp.repository.SubjectRepository;
import com.quizapp.service.SubjectService;
import jakarta.validation.Valid;
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
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;


    @GetMapping
    public String list(Model model) {
        log.info("Fetching all subjects");
        model.addAttribute("subjects", subjectService.findAll());
        log.info("Subjects added to model");
        return "subjects/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new subject");
        model.addAttribute("subject", new Subject());
        log.info("New subject object added to model");
        return "subjects/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {

        log.info("Displaying subject edit form for subject ID: {}", id);

        Subject subject = subjectService.findById(id);

        model.addAttribute("subject", subject);

        log.info("Subject object added to model");

        return "subjects/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("subject") Subject subject,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        log.info("Saving subject: {}", subject);

        try {
            subjectService.save(subject);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Subject saved successfully.");
            log.info("Subject saved successfully: {}", subject);

            return "redirect:/subjects";

        } catch (IllegalArgumentException e) {

            model.addAttribute("subject", subject);
            model.addAttribute("errorMessage", e.getMessage());
            log.info("Returning to form with error message for subject: {}", subject);

            return "subjects/form";
        }
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete subject with ID: {}", id);

        try {
            subjectService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Subject deleted successfully.");
        } catch (DataIntegrityViolationException e) {
            log.error("Cannot delete subject. It is assigned to another table.", e);

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Subject is assigned to another table and cannot be deleted.");
        }

        return "redirect:/subjects";
    }
}
