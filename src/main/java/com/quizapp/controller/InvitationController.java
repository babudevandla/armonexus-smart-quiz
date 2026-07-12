package com.quizapp.controller;

import com.quizapp.entity.Invitation;
import com.quizapp.repository.InvitationRepository;
import com.quizapp.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationRepository invitationRepository;
    private final QuizRepository quizRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("invitations", invitationRepository.findAll());
        model.addAttribute("quizzes", quizRepository.findAll());
        return "invitations/list";
    }

    @PostMapping("/save")
    public String save(@RequestParam Long quizId,
                        @RequestParam String email,
                        RedirectAttributes redirectAttributes) {
        Invitation invitation = Invitation.builder()
                .quiz(quizRepository.findById(quizId).orElseThrow())
                .email(email)
                .status("PENDING")
                .build();
        invitationRepository.save(invitation);
        redirectAttributes.addFlashAttribute("successMessage", "Invitation sent to " + email + ".");
        return "redirect:/invitations";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        invitationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Invitation removed.");
        return "redirect:/invitations";
    }
}
