package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SmsNotificationRepository smsNotificationRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final PushNotificationRepository pushNotificationRepository;
    private final AnnouncementRepository announcementRepository;

    @GetMapping("/sms")
    public String sms(Model model) {
        model.addAttribute("items", smsNotificationRepository.findAll());
        return "notifications/sms";
    }

    @PostMapping("/sms/send")
    public String sendSms(@RequestParam String recipientPhone, @RequestParam String message,
                           RedirectAttributes redirectAttributes) {
        smsNotificationRepository.save(SmsNotification.builder()
                .recipientPhone(recipientPhone).message(message).status("SENT").build());
        redirectAttributes.addFlashAttribute("successMessage", "SMS queued for delivery.");
        return "redirect:/notifications/sms";
    }

    @GetMapping("/email")
    public String email(Model model) {
        model.addAttribute("items", emailNotificationRepository.findAll());
        return "notifications/email";
    }

    @PostMapping("/email/send")
    public String sendEmail(@RequestParam String recipientEmail, @RequestParam String subject,
                             @RequestParam String body, RedirectAttributes redirectAttributes) {
        emailNotificationRepository.save(EmailNotification.builder()
                .recipientEmail(recipientEmail).subject(subject).body(body).status("SENT").build());
        redirectAttributes.addFlashAttribute("successMessage", "Email queued for delivery.");
        return "redirect:/notifications/email";
    }

    @GetMapping("/push")
    public String push(Model model) {
        model.addAttribute("items", pushNotificationRepository.findAll());
        return "notifications/push";
    }

    @PostMapping("/push/send")
    public String sendPush(@RequestParam String title, @RequestParam String body,
                            RedirectAttributes redirectAttributes) {
        pushNotificationRepository.save(PushNotification.builder()
                .title(title).body(body).status("SENT").build());
        redirectAttributes.addFlashAttribute("successMessage", "Push notification queued.");
        return "redirect:/notifications/push";
    }

    @GetMapping("/announcements")
    public String announcements(Model model) {
        model.addAttribute("items", announcementRepository.findAll());
        return "notifications/announcements";
    }

    @PostMapping("/announcements/save")
    public String saveAnnouncement(@RequestParam String title, @RequestParam String message,
                                    RedirectAttributes redirectAttributes) {
        announcementRepository.save(Announcement.builder().title(title).message(message).active(true).build());
        redirectAttributes.addFlashAttribute("successMessage", "Announcement published.");
        return "redirect:/notifications/announcements";
    }

    @GetMapping("/announcements/{id}/delete")
    public String deleteAnnouncement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        announcementRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement removed.");
        return "redirect:/notifications/announcements";
    }
}
