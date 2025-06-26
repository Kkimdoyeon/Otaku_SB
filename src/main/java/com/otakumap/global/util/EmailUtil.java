package com.otakumap.global.util;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email.", e);
        }
    }
}