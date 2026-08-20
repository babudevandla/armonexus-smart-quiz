package com.quizapp.controller;

import com.quizapp.entity.Invitation;
import com.quizapp.repository.InvitationRepository;
import com.quizapp.repository.QuizRepository;
import com.quizapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationRepository invitationRepository;
    private final QuizRepository quizRepository;
    private final EmailService emailService;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all invitations and quizzes");
        model.addAttribute("invitations", invitationRepository.findAll());
        model.addAttribute("quizzes", quizRepository.findAll());
        log.info("Invitations and quizzes added to model");
        return "invitations/list";
    }

    @PostMapping("/save")
    public String save(@RequestParam Long quizId,
                       @RequestParam String email,
                       RedirectAttributes redirectAttributes) {
        log.info("Creating invitation for quiz ID: {} and email: {}", quizId, email);

        Invitation invitation = Invitation.builder()
                .quiz(quizRepository.findById(quizId).orElseThrow())
                .email(email)
                .status("PENDING")
                .build();

        // Save invitation to database
        invitationRepository.save(invitation);

        // Send email
        log.info("Sending invitation email to: {}", email);
        emailService.sendInvitationEmail(
                email,
                invitation.getQuiz().getTitle()
        );

        log.info("Invitation sent to: {}", email);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Invitation sent to " + email + "."
        );
         log.info("Redirecting to invitations list");
        return "redirect:/invitations";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Deleting invitation with ID: {}", id);
        invitationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Invitation removed.");
        log.info("Invitation deleted, redirecting to invitations list");
        return "redirect:/invitations";
    }

}
