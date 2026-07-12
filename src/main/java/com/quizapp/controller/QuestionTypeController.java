package com.quizapp.controller;

import com.quizapp.entity.QuestionType;
import com.quizapp.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question-types")
@RequiredArgsConstructor
public class QuestionTypeController {

    private final QuestionTypeRepository questionTypeRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("types", questionTypeRepository.findAll());
        return "questiontypes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("type", new QuestionType());
        return "questiontypes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("type", questionTypeRepository.findById(id).orElseThrow());
        return "questiontypes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("type") QuestionType type, RedirectAttributes redirectAttributes) {
        questionTypeRepository.save(type);
        redirectAttributes.addFlashAttribute("successMessage", "Question type saved successfully.");
        return "redirect:/question-types";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        questionTypeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Question type deleted successfully.");
        return "redirect:/question-types";
    }
}
