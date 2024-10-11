package com.traineemanagement.userservice.service.incommunicationservice;

import com.traineemanagement.userservice.dto.specializationdto.SpecializationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "specialization-service", url = "${specialization.service.url}")
public interface SpecializationClient {

    @GetMapping("/api/specialization/{id}")
    SpecializationDTO getSpecializationById(@PathVariable Long id);

    @GetMapping("/api/specialization")
    List<SpecializationDTO> getAllSpecializations();
}
