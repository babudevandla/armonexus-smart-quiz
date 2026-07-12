package com.quizapp.controller;

import com.quizapp.entity.UserGroup;
import com.quizapp.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupRepository userGroupRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("groups", userGroupRepository.findAll());
        return "usergroups/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("group", new UserGroup());
        return "usergroups/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("group", userGroupRepository.findById(id).orElseThrow());
        return "usergroups/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("group") UserGroup group, RedirectAttributes redirectAttributes) {
        userGroupRepository.save(group);
        redirectAttributes.addFlashAttribute("successMessage", "User group saved successfully.");
        return "redirect:/user-groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userGroupRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User group deleted successfully.");
        return "redirect:/user-groups";
    }
}
