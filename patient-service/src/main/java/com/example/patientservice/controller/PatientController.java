package com.example.patientservice.controller;

import com.example.patientservice.data.Order;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.model.Patient;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        log.info("New patient registration {}", patientRequest);
        return new ResponseEntity<>(
                patientService.createPatient(patientRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") String patientId,
                                                 @Valid @RequestBody PatientRequest patientRequest) {
        log.info("Patient updated {}", patientRequest);
        return new ResponseEntity<>(
                patientService.updatePatient(patientId, patientRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/deactivate/{patientId}")
    public ResponseEntity<HttpStatus> deactivatePatient(@PathVariable("patientId") String patientId) {
        log.info("Patient deactivated with patientId {}", patientId);
        patientService.deactivatePatient(patientId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<JSONObject, List<Order>>> getPatientListWithTheirActiveOrders() {
        log.info("Get patients with their orders");
        return new ResponseEntity<>(patientService.getPatientListWithTheirActiveOrders(), HttpStatus.OK);
    }
}
