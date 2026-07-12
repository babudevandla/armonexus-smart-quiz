package com.quizapp.controller;

import com.quizapp.entity.Tag;
import com.quizapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tags", tagRepository.findAll());
        return "tags/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "tags/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagRepository.findById(id).orElseThrow());
        return "tags/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("tag") Tag tag, RedirectAttributes redirectAttributes) {
        tagRepository.save(tag);
        redirectAttributes.addFlashAttribute("successMessage", "Tag saved successfully.");
        return "redirect:/tags";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Tag deleted successfully.");
        return "redirect:/tags";
    }
}
