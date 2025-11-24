package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.model.Panchayath;
import com.onevoice.management.onevoice.repository.IndividualRepository;
import com.onevoice.management.onevoice.repository.PanchayathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private PanchayathRepository panchayathRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Individual individual = individualRepository.findByEmail(email).orElse(null);
        if (individual != null) {
            return User.builder()
                    .username(individual.getEmail())
                    .password(individual.getPassword())
                    .roles(individual.getRole())
                    .build();
        }

        Panchayath panchayath = panchayathRepository.findByEmail(email).orElse(null);
        if (panchayath != null) {
            return User.builder()
                    .username(panchayath.getEmail())
                    .password(panchayath.getPassword())
                    .roles(panchayath.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
