package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.Complaint;
import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.repository.IndividualRepository;
import com.onevoice.management.onevoice.service.ComplaintServiceImplimentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("/complaints")
public class ComplaintContoller {
    public ComplaintContoller(){
        System.out.println("ComplaintController constructor is Running");
    }

    @Autowired
    private ComplaintServiceImplimentation complaintService;

    @Autowired
    private IndividualRepository individualRepository;

    @PostMapping("/add")
    public Complaint addComplaint(@RequestParam String villageName,
                                  @RequestParam String areaName,
                                  @RequestParam String issueDescription,
                                  @RequestParam(required = false) MultipartFile image,
                                  @RequestParam(required = false) MultipartFile video,
                                  @RequestParam Double latitude,
                                  @RequestParam Double longitude,
                                  Principal principal) throws IOException {


        String username = principal.getName();

        Individual user = individualRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        return complaintService.registerComplaint(user, villageName, areaName,
                issueDescription, image, video, latitude, longitude);
    }



}
