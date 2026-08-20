package com.quizapp.controller;

import com.quizapp.entity.Announcement;
import com.quizapp.entity.EmailNotification;
import com.quizapp.entity.PushNotification;
import com.quizapp.entity.SmsNotification;
import com.quizapp.repository.AnnouncementRepository;
import com.quizapp.repository.EmailNotificationRepository;
import com.quizapp.repository.PushNotificationRepository;
import com.quizapp.repository.SmsNotificationRepository;
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
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SmsNotificationRepository smsNotificationRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final PushNotificationRepository pushNotificationRepository;
    private final AnnouncementRepository announcementRepository;
    private final EmailService emailService;

    @GetMapping("/sms")
    public String sms(Model model) {
        log.info("Fetching all SMS notifications");
        model.addAttribute("items", smsNotificationRepository.findAll());
        log.info("SMS notifications added to model");
        return "notifications/sms";
    }

    @PostMapping("/sms/send")
    public String sendSms(@RequestParam String recipientPhone, @RequestParam String message,
                           RedirectAttributes redirectAttributes) {
        log.info("Sending SMS to: {}", recipientPhone);
        smsNotificationRepository.save(SmsNotification.builder()
                .recipientPhone(recipientPhone).message(message).status("SENT").build());
        redirectAttributes.addFlashAttribute("successMessage", "SMS queued for delivery.");
        log.info("SMS notification saved to repository for recipient: {}", recipientPhone);
        return "redirect:/notifications/sms";
    }

    @GetMapping("/email")
    public String email(Model model) {
        log.info("Fetching all email notifications");
        model.addAttribute("items", emailNotificationRepository.findAll());
        log.info("Email notifications added to model");
        return "notifications/email";
    }

    @PostMapping("/email/send")
    public String sendEmail(
            @RequestParam String recipientEmail,
            @RequestParam String subject,
            @RequestParam String body,
            RedirectAttributes redirectAttributes) {
        log.info("Preparing to send email to: {}", recipientEmail);

        EmailNotification notification = EmailNotification.builder()
                .recipientEmail(recipientEmail)
                .subject(subject)
                .body(body)
                .status("PENDING")
                .build();

        try {

            // Actually send the email
            emailService.sendEmail(
                    recipientEmail,
                    subject,
                    body
            );

            // Only mark SENT after successful mailSender.send()
            notification.setStatus("SENT");

            emailNotificationRepository.save(notification);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Email sent successfully to " + recipientEmail
            );

        } catch (Exception e) {

            // Email was not sent
            notification.setStatus("FAILED");

            emailNotificationRepository.save(notification);

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Email sending failed: " + e.getMessage()
            );

            e.printStackTrace();
        }
        log.info("Email notification status for {}: {}", recipientEmail, notification.getStatus());

        return "redirect:/notifications/email";
    }

    @GetMapping("/push")
    public String push(Model model) {
        log.info("Fetching all push notifications");
        model.addAttribute("items", pushNotificationRepository.findAll());
        log.info("Push notifications added to model");
        return "notifications/push";
    }

    @PostMapping("/push/send")
    public String sendPush(@RequestParam String title, @RequestParam String body,
                            RedirectAttributes redirectAttributes) {
        log.info("Sending push notification with title: {}", title);
        pushNotificationRepository.save(PushNotification.builder()
                .title(title).body(body).status("SENT").build());
        redirectAttributes.addFlashAttribute("successMessage", "Push notification queued.");
        log.info("Push notification saved to repository with title: {}", title);
        return "redirect:/notifications/push";
    }

    @GetMapping("/announcements")
    public String announcements(Model model) {
        log.info("Fetching all announcements");
        model.addAttribute("items", announcementRepository.findAll());
        log.info("Announcements added to model");
        return "notifications/announcements";
    }

    @PostMapping("/announcements/save")
    public String saveAnnouncement(
            @RequestParam String title,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {

        log.info("Saving announcement with title: {}", title);

        announcementRepository.save(
                Announcement.builder()
                        .title(title)
                        .message(message)
                        .active(true)
                        .build()
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Announcement published to all users."
        );

        log.info("Announcement published to all users: {}", title);

        return "redirect:/notifications/announcements";
    }

    @GetMapping("/announcements/{id}/delete")
    public String deleteAnnouncement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Deleting announcement with ID: {}", id);
        announcementRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement removed.");
        log.info("Announcement deleted with ID: {}", id);
        return "redirect:/notifications/announcements";
    }
}
