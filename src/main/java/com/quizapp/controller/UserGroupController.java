package com.quizapp.controller;

import com.quizapp.entity.UserGroup;
import com.quizapp.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupRepository userGroupRepository;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all user groups");
        model.addAttribute("groups", userGroupRepository.findAll());
        log.info("User groups added to model");
        return "usergroups/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying user group creation form");
        model.addAttribute("group", new UserGroup());
        log.info("New user group object added to model");
        return "usergroups/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        log.info("Displaying user group edit form for group ID: {}", id);
        model.addAttribute("group", userGroupRepository.findById(id).orElseThrow());
        log.info("User group object added to model");
        return "usergroups/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("group") UserGroup group,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        log.info("Saving user group: {}", group);

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
        log.info("Attempting to delete user group with ID: {}", id);

        userGroupRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "User group deleted successfully.");
        log.info("User group with ID: {} deleted successfully", id);

        return "redirect:/user-groups";
    }
}
