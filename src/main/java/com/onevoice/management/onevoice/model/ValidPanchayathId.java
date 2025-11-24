package com.onevoice.management.onevoice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "valid_panchayath_ids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidPanchayathId {
    @Id
    private String id;
}
