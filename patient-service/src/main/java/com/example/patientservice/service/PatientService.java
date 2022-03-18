package com.example.patientservice.service;

import com.example.patientservice.client.OrderConsumer;
import com.example.patientservice.data.Order;
import com.example.patientservice.data.PatientState;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final OrderConsumer orderConsumer;

    public Patient createPatient(Patient patient) {
        patient.setCreateDateTimeGmt(LocalDate.now());
        patient.setUpdateDateTimeGmt(LocalDate.now());

        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        Optional<Patient> foundPatient = Optional.ofNullable(patientRepository
                .findById(patient.getPatientId())
                .orElseThrow(NullPointerException::new));

        foundPatient.ifPresent(patient_ -> {
            if (patient.getPatientState() != null) {
                patient_.setPatientState(patient.getPatientState());
            }
            if (patient.getFirstName() != null) {
                patient_.setFirstName(patient.getFirstName());
            }
            if (patient.getLastName() != null) {
                patient_.setLastName(patient.getLastName());
            }
            patientRepository.save(patient_);
        });
        return foundPatient.orElseThrow(NullPointerException::new);
    }

    public Patient deactivatePatient(Patient patient) {
        Optional<Patient> foundPatient = patientRepository.findById(patient.getPatientId());

        foundPatient.ifPresent(patient_ -> {
            patient_.setPatientState(PatientState.INACTIVE.name());
            patientRepository.save(patient_);
        });
        return foundPatient.orElseThrow(NullPointerException::new);
    }

    public Map<JSONObject, List<Order>> getPatientListWithTheirActiveOrders() {
        Map<String, List<Order>> activeOrders = orderConsumer.getAllActiveOrders().stream()
                .collect(Collectors.groupingBy(Order::getPatientId));

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
