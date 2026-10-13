package com.example.bloodbank.controller;

import com.example.bloodbank.model.BloodInventory;
import com.example.bloodbank.repository.BloodInventoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private static final List<String> GROUPS = List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    private final BloodInventoryRepository inventory;
    public InventoryController(BloodInventoryRepository inventory) { this.inventory = inventory; }

    @GetMapping
    public List<BloodInventory> all() {
        for (String group : GROUPS) inventory.findById(group).orElseGet(() -> inventory.save(new BloodInventory(group, 0)));
        return inventory.findAll().stream().sorted((a,b) -> a.getBloodGroup().compareTo(b.getBloodGroup())).toList();
    }

    @PutMapping("/{bloodGroup}")
    @PreAuthorize("hasRole('ADMIN')")
    public BloodInventory update(@PathVariable String bloodGroup, @Valid @RequestBody StockUpdate update) {
        String group = bloodGroup.toUpperCase();
        if (!GROUPS.contains(group)) throw new IllegalArgumentException("Invalid blood group");
        BloodInventory item = inventory.findById(group).orElse(new BloodInventory(group, 0));
        item.setUnits(update.units());
        return inventory.save(item);
    }

    public record StockUpdate(@Min(0) int units) {}
}
