package com.onevoice.management.onevoice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OtpService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStorage = new HashMap<>();

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit OTP
        otpStorage.put(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("One Voice - OTP Verification");
        message.setText("Your OTP code is: " + otp + "\n\nValid for 5 minutes.");
        mailSender.send(message);
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email); // remove after verification
            return true;
        }
        return false;
    }
}
