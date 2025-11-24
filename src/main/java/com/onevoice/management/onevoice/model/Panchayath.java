package com.onevoice.management.onevoice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "panchayaths")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Panchayath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(unique=true, nullable=false)
    private String phone;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, unique=true)
    private String oneVoicePanchayathId;

    @Column(nullable = false)
    private String role = "PANCHAYATH";
}
