package com.example.patientservice.service;

import com.example.patientservice.persistence.Patient;
import com.example.patientservice.persistence.PatientRepository;
import com.example.patientservice.dto.PatientRequest;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(PatientRequest request) {
        Patient patient = Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .patientState(request.getPatientState())
                .build();

        return patientRepository.save(patient);
    }
}
