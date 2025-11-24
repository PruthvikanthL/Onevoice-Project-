package com.onevoice.management.onevoice.repository;

import com.onevoice.management.onevoice.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual,Long> {
    Optional<Individual> findByEmail(String email);
    @Query("SELECT i FROM Individual i WHERE i.email = :email")
    Individual findByEmailId(@Param("email") String email);
}
