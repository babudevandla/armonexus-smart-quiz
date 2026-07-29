package com.quizapp.controller;

import com.quizapp.entity.UserGroup;
import com.quizapp.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("group", userGroupRepository.findById(id).orElseThrow());
        return "usergroups/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("group") UserGroup group,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        boolean duplicate;

        if (group.getId() == null) {
            // New group
            duplicate = userGroupRepository.existsByName(group.getName());
        } else {
            // Edit group
            duplicate = userGroupRepository.existsByNameAndIdNot(group.getName(), group.getId());
        }

        if (duplicate) {
            model.addAttribute("errorMessage", " group name already exists.");
            return "usergroups/form";
        }

        userGroupRepository.save(group);
        redirectAttributes.addFlashAttribute("successMessage", "group name saved successfully.");
        return "redirect:/user-groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {

        userGroupRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "User group deleted successfully.");

        return "redirect:/user-groups";
    }
}
