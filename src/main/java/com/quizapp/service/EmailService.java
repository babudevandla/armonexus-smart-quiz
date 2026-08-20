package com.quizapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String recipientEmail, String subject, String body) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("kambamravi518@gmail.com");
            message.setTo(recipientEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            log.info("Email sent successfully to {}", recipientEmail);

        } catch (Exception e) {

            log.error("Failed to send email to {}", recipientEmail, e);

            throw e;
        }
    }

    public void sendInvitationEmail(String to, String quizTitle) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("kambamravi518@gmail.com");
            message.setTo(to);
            message.setSubject("Quiz Invitation - " + quizTitle);

            message.setText(
                    "Hello,\n\n" +
                            "You have been invited to take the quiz: " + quizTitle + ".\n\n" +
                            "Please login to the Quiz Application to take the quiz.\n\n" +
                            "Regards,\n" +
                            "Quiz Admin"
            );

            mailSender.send(message);

            log.info("Quiz invitation sent successfully to {}", to);

        } catch (Exception e) {

            log.error("Failed to send quiz invitation to {}", to, e);

            throw e;
        }
    }
}