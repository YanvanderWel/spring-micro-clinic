package com.example.patientservice.controller;

import com.example.patientservice.data.Order;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientMapper patientMapper;
    private final PatientService patientService;


    @PostMapping("/create")
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        return new ResponseEntity<>(
                patientService.createPatient(patientMapper.patientRequestToPatient(patientRequest)),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") String id,
                                                 @RequestBody PatientRequest patientRequest) {
        log.info("Patient updated {}", patientRequest);
        return new ResponseEntity<>(
                patientService.updatePatient(id, patientMapper.patientRequestToPatient(patientRequest)),
                HttpStatus.OK);
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<HttpStatus> deactivatePatient(@PathVariable("id") String id) {
        log.info("Patient deactivated with id {}", id);
        patientService.deactivatePatient(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<JSONObject, List<Order>>> getPatientListWithTheirActiveOrders() {
        log.info("Get patients with their orders");
        return new ResponseEntity<>(patientService.getPatientListWithTheirActiveOrders(), HttpStatus.OK);
    }
}
