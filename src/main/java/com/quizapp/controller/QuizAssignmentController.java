package com.quizapp.controller;

import com.quizapp.entity.QuizAssignment;
import com.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quiz-assignments")
@RequiredArgsConstructor
public class QuizAssignmentController {

    private final QuizAssignmentRepository quizAssignmentRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @GetMapping("/users")
    public String assignUsers(Model model) {
        List<QuizAssignment> assignments = quizAssignmentRepository.findAll().stream()
                .filter(a -> a.getUser() != null)
                .toList();
        model.addAttribute("assignments", assignments);
        model.addAttribute("quizzes", quizRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "quizassignments/users";
    }

    @PostMapping("/users/save")
    public String saveUserAssignment(@RequestParam Long quizId,
                                      @RequestParam Long userId,
                                      RedirectAttributes redirectAttributes) {
        QuizAssignment assignment = QuizAssignment.builder()
                .quiz(quizRepository.findById(quizId).orElseThrow())
                .user(userRepository.findById(userId).orElseThrow())
                .build();
        quizAssignmentRepository.save(assignment);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz assigned to user successfully.");
        return "redirect:/quiz-assignments/users";
    }

    @GetMapping("/groups")
    public String assignGroups(Model model) {
        List<QuizAssignment> assignments = quizAssignmentRepository.findAll().stream()
                .filter(a -> a.getGroup() != null)
                .toList();
        model.addAttribute("assignments", assignments);
        model.addAttribute("quizzes", quizRepository.findAll());
        model.addAttribute("groups", userGroupRepository.findAll());
        return "quizassignments/groups";
    }

    @PostMapping("/groups/save")
    public String saveGroupAssignment(@RequestParam Long quizId,
                                       @RequestParam Long groupId,
                                       RedirectAttributes redirectAttributes) {
        QuizAssignment assignment = QuizAssignment.builder()
                .quiz(quizRepository.findById(quizId).orElseThrow())
                .group(userGroupRepository.findById(groupId).orElseThrow())
                .build();
        quizAssignmentRepository.save(assignment);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz assigned to group successfully.");
        return "redirect:/quiz-assignments/groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean wasUserAssignment = quizAssignmentRepository.findById(id)
                .map(a -> a.getUser() != null).orElse(true);
        quizAssignmentRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Assignment removed.");
        return wasUserAssignment ? "redirect:/quiz-assignments/users" : "redirect:/quiz-assignments/groups";
    }
}
