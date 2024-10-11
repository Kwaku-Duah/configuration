package com.specialization_management.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cohort-service", url = "${cohort.service.url}")
public interface CohortClient {

    @GetMapping("/api/cohort/{id}")
    Boolean isCohortExists(@PathVariable Long id);
}
