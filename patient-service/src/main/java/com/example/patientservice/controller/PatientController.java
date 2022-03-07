package com.example.patientservice.controller;

import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.data.Order;
import com.example.patientservice.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public void createPatient(@RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        patientService.createPatient(PatientMapper.INSTANCE.patientRequestToPatient(patientRequest));
    }

    @PostMapping("/update")
    public void updatePatient(@RequestBody PatientRequest patientRequest) {
        log.info("Patient updated {}", patientRequest);
        patientService.updatePatient(patientRequest);
    }

    @PostMapping("/deactivate")
    public void deactivatePatient(@RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        patientService.createPatient(PatientMapper.INSTANCE.patientRequestToPatient(patientRequest));
    }

    @GetMapping("/all")
    public Map<JSONObject, List<Order>> getPatientListWithTheirActiveOrders() {
        log.info("Get patient with their orders");
        return patientService.getPatientListWithTheirActiveOrders();
    }
}
