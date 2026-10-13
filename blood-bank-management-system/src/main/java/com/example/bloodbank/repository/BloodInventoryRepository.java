package com.example.bloodbank.repository;
import com.example.bloodbank.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {}
