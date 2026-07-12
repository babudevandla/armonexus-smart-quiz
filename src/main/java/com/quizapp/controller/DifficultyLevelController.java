package com.quizapp.controller;

import com.quizapp.entity.DifficultyLevel;
import com.quizapp.repository.DifficultyLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/difficulty-levels")
@RequiredArgsConstructor
public class DifficultyLevelController {

    private final DifficultyLevelRepository difficultyLevelRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("levels", difficultyLevelRepository.findAll());
        return "difficultylevels/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("level", new DifficultyLevel());
        return "difficultylevels/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("level", difficultyLevelRepository.findById(id).orElseThrow());
        return "difficultylevels/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("level") DifficultyLevel level, RedirectAttributes redirectAttributes) {
        difficultyLevelRepository.save(level);
        redirectAttributes.addFlashAttribute("successMessage", "Difficulty level saved successfully.");
        return "redirect:/difficulty-levels";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        difficultyLevelRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Difficulty level deleted successfully.");
        return "redirect:/difficulty-levels";
    }
}
