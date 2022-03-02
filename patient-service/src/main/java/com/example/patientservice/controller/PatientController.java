package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.Order;
import com.example.patientservice.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public List<Order> getPatientOrders(@RequestParam String patient_id) {
        log.info("Get orders with patient id {}", patient_id);
        return patientService.getPatientOrders(patient_id);
    }
}
