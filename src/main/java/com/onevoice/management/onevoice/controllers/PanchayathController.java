package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.OtpRequest;
import com.onevoice.management.onevoice.model.Panchayath;
import com.onevoice.management.onevoice.service.PanchayathService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/panchayath-api")
public class PanchayathController {

    @Autowired
    private PanchayathService panchayathService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email,
                                     @RequestParam String oneVoiceId) throws MessagingException {
        String result = panchayathService.sendOtp(email, oneVoiceId);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
        try {
            Panchayath p = request.getPanchayath();
            p.setEmail(request.getEmail());
            Panchayath saved = panchayathService.verifyOtpAndRegister(
                    request.getEmail(), request.getOtp(), p
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Registration successful",
                    "panchayath", saved
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
