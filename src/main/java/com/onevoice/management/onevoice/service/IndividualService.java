package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndividualService {
    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerIndividual(String name, String email, String phone, String password, String otp) {
        if (!otpService.validateOtp(email, otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        Individual ind = new Individual();
        ind.setName(name);
        ind.setEmail(email);
        ind.setPhone(phone);
        ind.setPassword(passwordEncoder.encode(password));
        individualRepository.save(ind); // no boolean return
    }

    public Individual findByEmailId(String email){
        return individualRepository.findByEmailId(email);
    }
}
