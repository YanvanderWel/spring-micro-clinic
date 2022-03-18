package com.example.patientservice.controller;

import com.example.patientservice.data.Order;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientMapper patientMapper;
    private final PatientService patientService;


    @PostMapping("/create")
    public Patient createPatient(@RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        return patientService.createPatient(patientMapper.patientRequestToPatient(patientRequest));
    }

    @PutMapping("/update")
    public void updatePatient(@RequestBody PatientRequest patientRequest) {
        log.info("Patient updated {}", patientRequest);
        patientService.updatePatient(patientMapper.patientRequestToPatient(patientRequest));
    }

    @PutMapping ("/deactivate")
    public void deactivatePatient(@RequestBody PatientRequest patientRequest) {
        log.info("Patient deactivated {}", patientRequest);
        patientService.deactivatePatient(patientMapper.patientRequestToPatient(patientRequest));
    }

    @GetMapping("/all")
    public Map<JSONObject, List<Order>> getPatientListWithTheirActiveOrders() {
        log.info("Get patient with their orders");
        return patientService.getPatientListWithTheirActiveOrders();
    }
}
