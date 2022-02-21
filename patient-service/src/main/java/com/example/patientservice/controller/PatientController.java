package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public void createPatient(@RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        patientService.createPatient(patientRequest);
    }
}
