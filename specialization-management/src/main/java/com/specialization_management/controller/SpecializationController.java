package com.specialization_management.controller;

import com.specialization_management.model.Specialization;
import com.specialization_management.service.SpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {
    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Specialization> createSpecialization(@RequestBody Specialization specialization) {
        Specialization newSpecialization = specializationService.createSpecialization(specialization);
        return ResponseEntity.ok().body(newSpecialization);
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        List<Specialization> specializations = specializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable Long id) {
        Optional<Specialization> specialization = specializationService.getSpecializationById(id);
        return specialization.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable Long id, @RequestBody Specialization specialization) {
        Specialization updatedSpecialization = specializationService.updateSpecialization(id, specialization);
        return ResponseEntity.ok().body(updatedSpecialization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}
