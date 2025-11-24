package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.Complaint;
import com.onevoice.management.onevoice.service.ComplaintServiceImplimentation;
import com.onevoice.management.onevoice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/panchayath")
public class PanchayathComplaintController {

    @Autowired
    private ComplaintServiceImplimentation complaintService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/complaints")
    public String viewComplaints(Model model, @RequestParam(required = false) String search) {
        List<Complaint> complaints = (search != null && !search.isEmpty())
                ? complaintService.searchComplaints(search)
                : complaintService.getAllComplaints();
        model.addAttribute("complaints", complaints);
        model.addAttribute("search", search);
        return "panchayath-complaints";
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestParam String toEmail,
                           @RequestParam String subject,
                           @RequestParam String message,
                           Model model) {
        try {
            emailService.sendMail(toEmail, subject, message);
            model.addAttribute("toEmail", toEmail);
            return "mail-success";
        } catch (Exception e) {
            return "mail-failure";
        }
    }


    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long complaintId,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        complaintService.updateComplaintStatus(complaintId, status);
        redirectAttributes.addFlashAttribute("success", "Status updated successfully!");
        return "redirect:/panchayath/complaints";
    }
}
