package com.traineemanagement.userservice.service.incommunicationservice;

import com.traineemanagement.userservice.dto.cohortdto.CohortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cohort-service", url = "${cohort.service.url}")
public interface CohortClient {

    @GetMapping("/api/cohort/{id}")
    CohortDto getCohortById(@PathVariable Long id);

    @GetMapping("/api/cohort")
    List<CohortDto> getAllCohorts();
}
