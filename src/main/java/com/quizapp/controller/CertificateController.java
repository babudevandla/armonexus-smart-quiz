package com.quizapp.controller;

import com.quizapp.entity.QuizResult;
import com.quizapp.entity.User;
import com.quizapp.repository.QuizResultRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.impl.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequiredArgsConstructor
public class CertificateController {

    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;
    private final CertificateService certificateService;

    /**
     * Generates (or re-generates) the certificate PDF for a passed quiz
     * result, then redirects back to the certificates list. Works for both
     * the Admin (any result) and the Student who owns that result.
     */
    @GetMapping("/certificates/{resultId}/generate")
    public String generate(@PathVariable Long resultId,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        QuizResult result = quizResultRepository.findById(resultId).orElseThrow();

        if (!canAccess(result, authentication)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not allowed to generate this certificate.");
            return "redirect:/certificates";
        }

        if (!Boolean.TRUE.equals(result.getPassed())) {
            redirectAttributes.addFlashAttribute("errorMessage", "A certificate can only be generated for a passed attempt.");
            return "redirect:/certificates";
        }

        try {
            certificateService.generate(result);
            result.setCertificateUrl("/certificates/" + result.getId() + "/download");
            quizResultRepository.save(result);
            redirectAttributes.addFlashAttribute("successMessage", "Certificate generated successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not generate certificate: " + e.getMessage());
        }

        return "redirect:/certificates";
    }

    @GetMapping("/certificates/{resultId}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long resultId, Authentication authentication) throws IOException {
        QuizResult result = quizResultRepository.findById(resultId).orElseThrow();

        if (!canAccess(result, authentication)) {
            return ResponseEntity.status(403).build();
        }

        Path filePath = Path.of("uploads/certificates/certificate_" + result.getId() + ".pdf");
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] bytes = Files.readAllBytes(filePath);
        String downloadName = "certificate_" + result.getUser().getFullName().replaceAll("\\s+", "_") + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    private boolean canAccess(QuizResult result, Authentication authentication) {
        User me = userRepository.findByEmail(authentication.getName()).orElseThrow();

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        return isAdmin || result.getUser().getId().equals(me.getId());
    }
}
