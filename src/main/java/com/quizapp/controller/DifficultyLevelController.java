package com.quizapp.controller;

import com.quizapp.entity.DifficultyLevel;
import com.quizapp.repository.DifficultyLevelRepository;
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
@RequestMapping("/difficulty-levels")
@RequiredArgsConstructor
public class DifficultyLevelController {

    private final DifficultyLevelRepository difficultyLevelRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all difficulty levels");
        model.addAttribute("levels", difficultyLevelRepository.findAll());
        log.info("Difficulty levels added to model");
        return "difficultylevels/list";

    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new difficulty level");
        model.addAttribute("level", new DifficultyLevel());
        log.info("New difficulty level object added to model");
        return "difficultylevels/form";

    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing difficulty level with ID: {}", id);
        model.addAttribute("level", difficultyLevelRepository.findById(id).orElseThrow());
        log.info("Difficulty level object added to model");
        return "difficultylevels/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("level") DifficultyLevel level, Model model, RedirectAttributes redirectAttributes) {
        try {
            log.info("Saving difficulty level: {}", level);
            difficultyLevelRepository.save(level);
            redirectAttributes.addFlashAttribute("successMessage", "Difficulty level saved successfully.");
            log.info("Difficulty level saved successfully: {}", level);
            return "redirect:/difficulty-levels";
        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate difficulty level name: {}", level.getName(), e);
            model.addAttribute("level", level);
            model.addAttribute("errorMessage", "A difficulty level with this name already exists. Please use a different name.");
            log.info("Returning to form with error message for difficulty level: {}", level);
            return "difficultylevels/form";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Deleting difficulty level with ID: {}", id);
        difficultyLevelRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Difficulty level deleted successfully.");
        log.info("Difficulty level with ID: {} deleted successfully", id);
        return "redirect:/difficulty-levels";
    }
}
