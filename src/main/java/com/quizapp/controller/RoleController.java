package com.quizapp.controller;

import com.quizapp.entity.Role;
import com.quizapp.repository.RoleRepository;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all roles and their user counts");
        List<Role> roles = roleRepository.findAll();

        Map<String, Long> userCounts = new HashMap<>();
        for (Role r : roles) {
            String key = String.valueOf(r.getId());
            userCounts.put(key, userRepository.countUsersByRoleId(r.getId()));
        }

        model.addAttribute("roles", roles);
        model.addAttribute("userCounts", userCounts);
        log.info("Roles and user counts added to model: {}", userCounts);
        return "roles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying role creation form");
        model.addAttribute("role", new Role());
        log.info("New role object added to model");
        return "roles/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        log.info("Displaying role edit form for role ID: {}", id);
        model.addAttribute("role", roleRepository.findById(id).orElseThrow());
        log.info("Role object added to model");
        return "roles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        log.info("Saving role: {}", role);
        roleRepository.save(role);
        redirectAttributes.addFlashAttribute("successMessage", "Role saved successfully.");
        log.info("Role saved successfully: {}", role);
        return "redirect:/roles";
    }

    @GetMapping("/{id}/delete")
    public String deleteRole(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete role with ID: {}", id);

        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Role not found.");
            return "redirect:/roles";
        }

        long assigned = userRepository.countUsersByRoleId(id);
        if (assigned > 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete this role because it is assigned to " + assigned + " user(s). Please unassign it first.");
            return "redirect:/roles";
        }

        roleRepository.delete(role);
        redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully.");
        log.info("Role with ID: {} deleted successfully", id);

        return "redirect:/roles";
    }
}

