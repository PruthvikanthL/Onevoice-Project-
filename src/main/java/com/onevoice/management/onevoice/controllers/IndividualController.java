package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.service.IndividualService;
import com.onevoice.management.onevoice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/individuals")
public class IndividualController {
    @Autowired
    private IndividualService individualService;
    @Autowired
    private OtpService otpService;
    @PostMapping(value = "/register", produces = "text/plain")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String otp) {
        try {
            individualService.registerIndividual(name, email, phone, password, otp);
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    @PostMapping(value = "/send-otp", produces = "text/plain")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        try {
            otpService.sendOtp(email);
            return ResponseEntity.ok("OTP sent successfully to " + email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

}
