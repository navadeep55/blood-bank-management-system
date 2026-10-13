package com.example.bloodbank.controller;

import com.example.bloodbank.model.Donor;
import com.example.bloodbank.repository.DonorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {
    private final DonorRepository donors;
    public DonorController(DonorRepository donors) { this.donors = donors; }

    @GetMapping public List<Donor> all() { return donors.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donor create(@Valid @RequestBody Donor donor) { return donors.save(donor); }
}
