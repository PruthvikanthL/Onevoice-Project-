package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.Complaint;
import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.service.ComplaintServiceImplimentation;
import com.onevoice.management.onevoice.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ComplaintViewController {

    @Autowired
    private ComplaintServiceImplimentation complaintService;

    @Autowired
    private IndividualService individualService;

    @GetMapping("/complaints/status")
    public String getComplaintStatus(Model model, Principal principal) {
        String email = principal.getName();
        Individual individual = individualService.findByEmailId(email);

        List<Complaint> complaints = complaintService.findByIndividualId(individual.getId());

        System.out.println("Complaints size for user " + individual.getEmail() + ": " + complaints.size());
        for (Complaint c : complaints) {
            System.out.println("Complaint -> ID:" + c.getId() +
                    ", Village:" + c.getVillageName() +
                    ", Area:" + c.getAreaName() +
                    ", Issue:" + c.getIssueDescription() +
                    ", Status:" + c.getStatus());
        }

        model.addAttribute("complaints", complaints);
        return "complaint-status";
    }
}
