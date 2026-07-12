package com.quizapp.controller;

import com.quizapp.entity.PracticeSet;
import com.quizapp.repository.PracticeAttemptRepository;
import com.quizapp.repository.PracticeSetRepository;
import com.quizapp.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PracticeSetController {

    private final PracticeSetRepository practiceSetRepository;
    private final SubjectRepository subjectRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;

    @GetMapping("/practice-sets")
    public String list(Model model) {
        model.addAttribute("practiceSets", practiceSetRepository.findAll());
        return "practicesets/list";
    }

    @GetMapping("/practice-sets/new")
    public String createForm(Model model) {
        model.addAttribute("practiceSet", new PracticeSet());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "practicesets/form";
    }

    @GetMapping("/practice-sets/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("practiceSet", practiceSetRepository.findById(id).orElseThrow());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "practicesets/form";
    }

    @PostMapping("/practice-sets/save")
    public String save(@ModelAttribute PracticeSet practiceSet, RedirectAttributes redirectAttributes) {
        practiceSetRepository.save(practiceSet);
        redirectAttributes.addFlashAttribute("successMessage", "Practice set saved successfully.");
        return "redirect:/practice-sets";
    }

    @GetMapping("/practice-sets/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        practiceSetRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Practice set deleted successfully.");
        return "redirect:/practice-sets";
    }

    @GetMapping("/practice-history")
    public String history(Model model) {
        model.addAttribute("attempts", practiceAttemptRepository.findAll());
        return "practicesets/history";
    }
}
