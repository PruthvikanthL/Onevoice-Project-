package com.onevoice.management.onevoice.repository;

import com.onevoice.management.onevoice.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByIndividualId(Long individualId);
    List<Complaint> findByVillageNameContainingIgnoreCaseOrAreaNameContainingIgnoreCaseOrIssueDescriptionContainingIgnoreCase(
            String village, String area, String issue
    );

}
