package com.onevoice.management.onevoice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "individual_id",nullable = false)
    private Individual individual;

    @Column(nullable = false)
    private String villageName;

    @Column(nullable = false)
    private String areaName;

    @Column(nullable = false,length = 1000)
    private String issueDescription;


    private String imagePath;

    private String videoPath;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status=ComplaintStatus.PENDING;
}
