package com.quizapp.controller;

import com.quizapp.entity.Category;
import com.quizapp.repository.CategoryRepository;
import com.quizapp.repository.SubjectRepository;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all categories");
        model.addAttribute("categories", categoryRepository.findAll());
        log.info("Categories added to model");
        return "categories/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new category");
        model.addAttribute("category", new Category());
        model.addAttribute("subjects", subjectRepository.findAll());
        log.info("New category object and subjects added to model");
        return "categories/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing category with ID: {}", id);
        model.addAttribute("category", categoryRepository.findById(id).orElseThrow());
        model.addAttribute("subjects", subjectRepository.findAll());
        log.info("Category object and subjects added to model");
        return "categories/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category, Model model, RedirectAttributes redirectAttributes) {
        log.info("Saving category: {}", category);
        var existingCategories = categoryRepository.findByNameIgnoreCase(category.getName());
        var duplicates = existingCategories.stream()
                .filter(c -> category.getId() == null || !c.getId().equals(category.getId()))
                .toList();
        
        if (!duplicates.isEmpty()) {
            log.warn("Duplicate category name found: {}", category.getName());
            model.addAttribute("category", category);
            model.addAttribute("subjects", subjectRepository.findAll());
            model.addAttribute("errorMessage", "A category with this name already exists. Please use a different name.");
            return "categories/form";
        }

        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully.");
        log.info("Category saved successfully: {}", category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete category with ID: {}", id);
        categoryRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully.");
        log.info("Category deleted successfully with ID: {}", id);
        return "redirect:/categories";
    }
}
