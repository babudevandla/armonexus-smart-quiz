package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final ApplicationSettingsRepository applicationSettingsRepository;
    private final SecuritySettingsRepository securitySettingsRepository;
    private final OtpSettingsRepository otpSettingsRepository;
    private final PaymentGatewaySettingsRepository paymentGatewaySettingsRepository;
    private final EmailConfigurationRepository emailConfigurationRepository;

    @GetMapping("/application")
    public String application(Model model) {
        model.addAttribute("settings", applicationSettingsRepository.findById(1L)
                .orElseGet(() -> ApplicationSettings.builder().id(1L).build()));
        return "settings/application";
    }

    @PostMapping("/application/save")
    public String saveApplication(@ModelAttribute ApplicationSettings settings, RedirectAttributes redirectAttributes) {
        settings.setId(1L);
        applicationSettingsRepository.save(settings);
        redirectAttributes.addFlashAttribute("successMessage", "Application settings saved.");
        return "redirect:/settings/application";
    }

    @GetMapping("/security")
    public String security(Model model) {
        model.addAttribute("settings", securitySettingsRepository.findById(1L)
                .orElseGet(() -> SecuritySettings.builder().id(1L).build()));
        return "settings/security";
    }

    @PostMapping("/security/save")
    public String saveSecurity(@ModelAttribute SecuritySettings settings, RedirectAttributes redirectAttributes) {
        settings.setId(1L);
        securitySettingsRepository.save(settings);
        redirectAttributes.addFlashAttribute("successMessage", "Security settings saved.");
        return "redirect:/settings/security";
    }

    @GetMapping("/otp")
    public String otp(Model model) {
        model.addAttribute("settings", otpSettingsRepository.findById(1L)
                .orElseGet(() -> OtpSettings.builder().id(1L).build()));
        return "settings/otp";
    }

    @PostMapping("/otp/save")
    public String saveOtp(@ModelAttribute OtpSettings settings, RedirectAttributes redirectAttributes) {
        settings.setId(1L);
        otpSettingsRepository.save(settings);
        redirectAttributes.addFlashAttribute("successMessage", "OTP settings saved.");
        return "redirect:/settings/otp";
    }

    @GetMapping("/payment-gateway")
    public String paymentGateway(Model model) {
        model.addAttribute("settings", paymentGatewaySettingsRepository.findById(1L)
                .orElseGet(() -> PaymentGatewaySettings.builder().id(1L).build()));
        return "settings/payment-gateway";
    }

    @PostMapping("/payment-gateway/save")
    public String savePaymentGateway(@ModelAttribute PaymentGatewaySettings settings, RedirectAttributes redirectAttributes) {
        settings.setId(1L);
        paymentGatewaySettingsRepository.save(settings);
        redirectAttributes.addFlashAttribute("successMessage", "Payment gateway settings saved.");
        return "redirect:/settings/payment-gateway";
    }

    @GetMapping("/email-configuration")
    public String emailConfiguration(Model model) {
        model.addAttribute("settings", emailConfigurationRepository.findById(1L)
                .orElseGet(() -> EmailConfiguration.builder().id(1L).build()));
        return "settings/email-configuration";
    }

    @PostMapping("/email-configuration/save")
    public String saveEmailConfiguration(@ModelAttribute EmailConfiguration settings, RedirectAttributes redirectAttributes) {
        settings.setId(1L);
        emailConfigurationRepository.save(settings);
        redirectAttributes.addFlashAttribute("successMessage", "Email configuration saved.");
        return "redirect:/settings/email-configuration";
    }
}
