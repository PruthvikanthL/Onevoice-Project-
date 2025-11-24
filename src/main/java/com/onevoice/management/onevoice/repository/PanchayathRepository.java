package com.onevoice.management.onevoice.repository;

import com.onevoice.management.onevoice.model.Panchayath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanchayathRepository extends JpaRepository<Panchayath, Long> {
    Optional<Panchayath> findByEmail(String email);
}
