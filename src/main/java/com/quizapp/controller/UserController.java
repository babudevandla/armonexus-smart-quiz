package com.quizapp.controller;

import com.quizapp.entity.Role;
import com.quizapp.entity.User;
import com.quizapp.repository.RoleRepository;
import com.quizapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all users");
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        log.info("Users added to model: {}", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying user creation form");
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("selectedRoleIds", new HashSet<Long>());
        log.info("New user object and roles added to model");
        return "users/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        log.info("Displaying user edit form for user ID: {}", id);
        User user = userService.findById(id);
        Set<Long> selectedRoleIds = new HashSet<>();
        for (Role r : user.getRoles()) {
            selectedRoleIds.add(r.getId());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("selectedRoleIds", selectedRoleIds);
        log.info("User object and roles added to model");
        return "users/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                        BindingResult bindingResult,
                        @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        log.info("Saving user: {}", user);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("selectedRoleIds", roleIds == null ? new HashSet<>() : roleIds);
            return "users/form";
        }

        boolean isNew = (user.getId() == null);
        userService.save(user, roleIds, isNew);
        redirectAttributes.addFlashAttribute("successMessage",
                "User " + (isNew ? "created" : "updated") + " successfully.");
        log.info("User saved successfully: {}", user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete user with ID: {}", id);
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        log.info("User with ID: {} deleted successfully", id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/toggle")
    public String toggle(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        log.info("Attempting to toggle user status for ID: {}", id);
        userService.toggleEnabled(id);
        redirectAttributes.addFlashAttribute("successMessage", "User status updated.");
        log.info("User status toggled successfully for ID: {}", id);
        return "redirect:/users";
    }
}
