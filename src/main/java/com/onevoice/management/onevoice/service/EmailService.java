package com.onevoice.management.onevoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String messageText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);


            String htmlMsg = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: 'Poppins', sans-serif; margin:0; padding:0; background:#f5f7fa; }" +
                    ".container { max-width:600px; margin:30px auto; background:white; border-radius:15px; overflow:hidden; box-shadow:0 8px 20px rgba(0,0,0,0.2); }" +
                    ".header { background:#0e0e0e; text-align:center; padding:25px; color:white; }" +
                    ".header h1 { font-size:28px; font-weight:900; margin:0;" +
                    " background: linear-gradient(90deg, #14b8a6, #3b82f6, #8b5cf6);" +
                    " -webkit-background-clip: text;" +
                    " -webkit-text-fill-color: transparent;" +
                    "}" +
                    ".content { padding:25px; color:#333; font-size:16px; line-height:1.5; }" +
                    ".content p { margin:15px 0; }" +
                    ".footer { background:#f0f0f0; padding:15px; font-size:12px; color:#666; text-align:center; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h1>One Voice</h1>" +
                    "<p>Panchayath Notification</p>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<p>Dear Citizen,</p>" +
                    "<p>" + messageText + "</p>" +
                    "<p>Thank you for using One Voice!</p>" +
                    "</div>" +
                    "<div class='footer'>" +
                    "<p>This is an automated message. Please do not reply.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";


            helper.setText(htmlMsg, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
