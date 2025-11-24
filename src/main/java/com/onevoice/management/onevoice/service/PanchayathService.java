package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Panchayath;
import com.onevoice.management.onevoice.repository.PanchayathRepository;
import com.onevoice.management.onevoice.repository.ValidPanchayathIdRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class PanchayathService {
    @Autowired
    private PanchayathRepository panchayathRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private ValidPanchayathIdRepository validPanchayathIdRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String sendOtp(String email, String oneVoiceId) throws MessagingException {
        if (!validPanchayathIdRepository.existsById(oneVoiceId)) {
            return "Invalid OneVoice ID ❌";
        }
        otpService.sendOtp(email);

        return "✅ OTP sent to email: " + email;
    }

    public Panchayath verifyOtpAndRegister(String email, String otp, Panchayath panchayath) {
        if (!otpService.validateOtp(email, otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        panchayath.setPassword(passwordEncoder.encode(panchayath.getPassword()));
        panchayath.setRole("PANCHAYATH");

        return panchayathRepository.save(panchayath);
    }
}
