package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.Order;
import com.example.patientservice.persistence.Patient;
import com.example.patientservice.persistence.PatientRepository;
import com.google.cloud.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient createPatient(PatientRequest request) {
        Patient patient = Patient.builder()
                .patient_id(UUID.randomUUID().toString())
                .create_date_time_gmt(LocalDate.now())
                .update_date_time_gmt(LocalDate.now())
                .first_name(request.getFirstName())
                .last_name(request.getLastName())
                .patient_state(request.getPatientState().name())
                .build();

        if (patient.getOrders() != null) {
            patient.getOrders().forEach(order -> {
//                order.setPatient_id(patient.getPatient_id());
                order.setOrder_id(new Order.OrderId(patient.getPatient_id(), UUID.randomUUID().toString()));
            });
        }

        return patientRepository.save(patient);
    }

    @Transactional
    public List<Order> getPatientOrders(String patient_id) {
        return patientRepository.getById(patient_id).getOrders();
    }
}
