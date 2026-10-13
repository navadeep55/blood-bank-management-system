package com.example.bloodbank.controller;

import com.example.bloodbank.model.BloodRequest;
import com.example.bloodbank.repository.BloodRequestRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {
    private final BloodRequestRepository requests;
    public BloodRequestController(BloodRequestRepository requests) { this.requests = requests; }

    @GetMapping public List<BloodRequest> all() { return requests.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodRequest create(@Valid @RequestBody BloodRequest request) {
        request.setStatus("PENDING");
        return requests.save(request);
    }
}
