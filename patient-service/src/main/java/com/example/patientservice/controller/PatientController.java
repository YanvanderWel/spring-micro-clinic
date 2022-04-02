package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientIdWrapper;
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
        ResponseEntity<Patient> patientResponseEntity = new ResponseEntity<>(
                patientService.createPatient(patientRequest),
                HttpStatus.CREATED);

        log.info("Patient {} was created", patientRequest);
        return patientResponseEntity;
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") String patientId,
                                                 @Valid @RequestBody PatientRequest patientRequest) {
        ResponseEntity<Patient> patientResponseEntity = new ResponseEntity<>(
                patientService.updatePatient(patientId, patientRequest),
                HttpStatus.OK);

        log.info("Patient with patientId {} was updated", patientRequest);
        return patientResponseEntity;
    }

    @DeleteMapping("/deactivate/{patientId}")
    public ResponseEntity<HttpStatus> deactivatePatient(@PathVariable("patientId") String patientId) {
        patientService.deactivatePatient(patientId);

        log.info("Patient with patientId {} was deactivated", patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<JSONObject, List<Order>>> getPatientListWithTheirActiveOrders(
            @RequestBody PatientIdWrapper patientIdsWrapper
    ) {
        log.info("Get patients with their active orders");
        return new ResponseEntity<>(
                patientService.getPatientListWithTheirActiveOrders(patientIdsWrapper),
                HttpStatus.OK
        );
    }
}
