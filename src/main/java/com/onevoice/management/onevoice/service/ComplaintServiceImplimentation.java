package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Complaint;
import com.onevoice.management.onevoice.model.ComplaintStatus;
import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ComplaintServiceImplimentation  {
    @Autowired
    private ComplaintRepository complaintRepository;

    private final String uploadDir="C:/Users/pruth/Downloads/onevoice/onevoice/uploads/";

    public Complaint registerComplaint(Individual user, String village, String area, String description,
                                       MultipartFile image, MultipartFile video,
                                       Double latitude, Double longitude) throws IOException {
        Complaint complaint = new Complaint();
        complaint.setIndividual(user);
        complaint.setVillageName(village);
        complaint.setAreaName(area);
        complaint.setIssueDescription(description);
        complaint.setLatitude(latitude);
        complaint.setLongitude(longitude);

        if(image != null && !image.isEmpty()){
            String imagePath = uploadDir + System.currentTimeMillis() + "_" + image.getOriginalFilename();
            image.transferTo(new File(imagePath));
            complaint.setImagePath(imagePath);
        }
        if(video != null && !video.isEmpty()){
            String vidPath = uploadDir + System.currentTimeMillis() + "_" + video.getOriginalFilename();
            video.transferTo(new File(vidPath));
            complaint.setVideoPath(vidPath);
        }

        return complaintRepository.save(complaint);
    }


    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }


    public List<Complaint> findByIndividualId(Long individualId) {
        return complaintRepository.findByIndividualId(individualId);
    }
    public List<Complaint> searchComplaints(String keyword) {
        return complaintRepository.findByVillageNameContainingIgnoreCaseOrAreaNameContainingIgnoreCaseOrIssueDescriptionContainingIgnoreCase(
                keyword, keyword, keyword);
    }


    public void updateComplaintStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            try {
                ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status.toUpperCase().replace(" ", "_"));
                complaint.setStatus(complaintStatus);
                complaintRepository.save(complaint);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status value: " + status);
            }
        }
    }



    public Complaint getComplaintById(Long id){
        return complaintRepository.findById(id).orElse(null);
    }

    public Complaint saveComplaint(Complaint complaint){
        return complaintRepository.save(complaint);
    }



}
