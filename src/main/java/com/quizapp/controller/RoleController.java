package com.quizapp.controller;

import com.quizapp.entity.Role;
import com.quizapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "roles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleRepository.findById(id).orElseThrow());
        return "roles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        roleRepository.save(role);
        redirectAttributes.addFlashAttribute("successMessage", "Role saved successfully.");
        return "redirect:/roles";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roleRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully.");
        return "redirect:/roles";
    }
}
