package com.example.bloodbank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 150) private String hospitalName;
    @NotBlank @Size(max = 100) private String contactName;
    @NotBlank @Pattern(regexp = "^[0-9+() -]{7,20}$") private String phone;
    @NotBlank @Pattern(regexp = "^(A|B|AB|O)[+-]$") private String bloodGroup;
    @Min(1) private int units;
    @Size(max = 500) private String notes;
    private String status = "PENDING";
    private LocalDateTime createdAt = LocalDateTime.now();

    public BloodRequest() {}
    @PrePersist void beforeInsert() { if (createdAt == null) createdAt = LocalDateTime.now(); if (status == null) status = "PENDING"; }
    public Long getId() { return id; }
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
