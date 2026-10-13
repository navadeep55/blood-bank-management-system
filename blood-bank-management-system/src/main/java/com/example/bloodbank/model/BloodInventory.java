package com.example.bloodbank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "blood_inventory")
public class BloodInventory {
    @Id
    @Column(length = 3)
    private String bloodGroup;

    @Min(0)
    private int units;

    public BloodInventory() {}
    public BloodInventory(String bloodGroup, int units) { this.bloodGroup = bloodGroup; this.units = units; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
}
