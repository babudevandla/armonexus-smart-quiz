package com.quizapp.controller;

import com.quizapp.entity.Tag;
import com.quizapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all tags");
        model.addAttribute("tags", tagRepository.findAll());
        log.info("Tags added to model");
        return "tags/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new tag");
        model.addAttribute("tag", new Tag());
        log.info("New tag object added to model");
        return "tags/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing tag with ID: {}", id);
        model.addAttribute("tag", tagRepository.findById(id).orElseThrow());
        log.info("Tag object added to model");
        return "tags/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("tag") Tag tag,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        log.info("Entered name = '{}'", tag.getName());

        boolean exists = tagRepository.existsByNameIgnoreCase(tag.getName().trim());

        log.info("Exists = {}", exists);

        if (tag.getId() == null && exists) {
            model.addAttribute("tag", tag);
            model.addAttribute("errorMessage", "Tag already exists.");
            return "tags/form";
        }

        tagRepository.save(tag);
        redirectAttributes.addFlashAttribute("successMessage", "Tag saved successfully.");
        log.info("Tag saved successfully: {}", tag);
        return "redirect:/tags";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagRepository.deleteById(id);
        log.info("Tag with ID: {} deleted successfully", id);
        redirectAttributes.addFlashAttribute("successMessage", "Tag deleted successfully.");
        log.info("Tag with ID: {} deleted successfully", id);
        return "redirect:/tags";
    }
}
