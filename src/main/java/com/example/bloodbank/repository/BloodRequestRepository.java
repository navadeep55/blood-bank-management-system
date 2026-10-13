package com.example.bloodbank.repository;
import com.example.bloodbank.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {}
