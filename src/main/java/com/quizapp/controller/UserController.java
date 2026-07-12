package com.quizapp.controller;

import com.quizapp.entity.Role;
import com.quizapp.entity.User;
import com.quizapp.repository.RoleRepository;
import com.quizapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String list(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("selectedRoleIds", new HashSet<Long>());
        return "users/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        Set<Long> selectedRoleIds = new HashSet<>();
        for (Role r : user.getRoles()) {
            selectedRoleIds.add(r.getId());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("selectedRoleIds", selectedRoleIds);
        return "users/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                        BindingResult bindingResult,
                        @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("selectedRoleIds", roleIds == null ? new HashSet<>() : roleIds);
            return "users/form";
        }

        boolean isNew = (user.getId() == null);
        userService.save(user, roleIds, isNew);
        redirectAttributes.addFlashAttribute("successMessage",
                "User " + (isNew ? "created" : "updated") + " successfully.");
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/users";
    }

    @GetMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.toggleEnabled(id);
        redirectAttributes.addFlashAttribute("successMessage", "User status updated.");
        return "redirect:/users";
    }
}
