package com.cohort_management.controller;

import com.cohort_management.Dto.CohortDto;
import com.cohort_management.Dto.SpecializationDTO;
import com.cohort_management.model.Cohort;
import com.cohort_management.service.CohortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cohort")
public class CohortController {
    private final CohortService cohortService;
    public CohortController(CohortService cohortService) {
        this.cohortService = cohortService;
    }

    // Get all specializations assigned to a cohort
    @GetMapping("/{id}/specializations")
    public ResponseEntity<List<SpecializationDTO>> getSpecializationsForCohort(@PathVariable Long id) {
        List<SpecializationDTO> specializations = cohortService.getSpecializationsForCohort(id);
        return new ResponseEntity<>(specializations, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Cohort>> getAllCohorts() {
        List<Cohort> cohorts = cohortService.getAllCohorts();
        if (cohorts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(cohorts);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cohort> getCohortById(@PathVariable Long id) {
        Optional<Cohort> cohort = cohortService.getCohortById(id);
        return cohort.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<CohortDto> createCohort(@RequestBody Cohort cohort) {
        CohortDto createdCohort = cohortService.createCohort(cohort);
        return ResponseEntity.ok(createdCohort);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cohort> updateCohort(@PathVariable Long id, @RequestBody Cohort cohort) {
        Cohort updatedCohort = cohortService.updateCohort(id, cohort);
        return ResponseEntity.ok(updatedCohort);
    }
}
