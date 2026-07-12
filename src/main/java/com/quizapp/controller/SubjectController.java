package com.quizapp.controller;

import com.quizapp.entity.Subject;
import com.quizapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        return "subjects/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        subjectService.save(subject);
        redirectAttributes.addFlashAttribute("successMessage", "Subject saved successfully.");
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        subjectService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Subject deleted successfully.");
        return "redirect:/subjects";
    }
}
