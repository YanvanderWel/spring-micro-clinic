package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.data.Order;
import com.example.patientservice.persistence.OrderConsumer;
import com.example.patientservice.persistence.Patient;
import com.example.patientservice.persistence.PatientRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final OrderConsumer orderConsumer;

    @Autowired
    public PatientService(PatientRepository patientRepository, OrderConsumer orderConsumer) {
        this.patientRepository = patientRepository;
        this.orderConsumer = orderConsumer;
    }

    @Transactional
    public Patient createPatient(Patient patient) {
        patient.setPatientId(UUID.randomUUID().toString());

        return patientRepository.save(patient);
    }

    @Transactional
    public void updatePatient(PatientRequest request) {
        Optional<Patient> patient = patientRepository.findById(request.getPatientId());

        patient.ifPresent(patient_ -> {
            if (request.getPatientState() != null) {
                patient_.setPatientState(request.getPatientState());
            }
            if (request.getFirstName() != null) {
                patient_.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                patient_.setLastName(request.getLastName());
            }
        });

    }

    @Transactional
    public Map<JSONObject, List<Order>> getPatientListWithTheirActiveOrders() {
        Map<String, List<Order>> activeOrders = orderConsumer.getAllActiveOrders().stream()
                .collect(Collectors.groupingBy(Order::getPatient_id));

        Map<JSONObject, List<Order>> patientsWithTheirOrders = new HashMap<>();
        for (Patient patient : patientRepository.findAll()) {
            if (activeOrders.get(patient.getPatientId()) != null) {
                patientsWithTheirOrders.put(new JSONObject(patient),
                        activeOrders.get(patient.getPatientId()));
            }
        }

        return patientsWithTheirOrders;
    }
}
