package com.example.patientservice.service;

import com.example.patientservice.client.OrderConsumer;
import com.example.patientservice.data.Order;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.patientservice.Utils.getTimestampNow;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final OrderConsumer orderConsumer;

    public Patient createPatient(PatientRequest patientRequest) {
        Patient patient = patientMapper.patientRequestToPatient(patientRequest);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(String patientId, PatientRequest patientRequest) {
        Patient patient = patientMapper.patientRequestToPatient(patientRequest);
        Patient foundPatient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Patient for updating not found");
                });

        updatePatient(patient, foundPatient);

        return patientRepository.save(foundPatient);
    }

    private void updatePatient(Patient patientFrom, Patient patientTo) {
        if (patientFrom.getPatientState() != null) {
            patientTo.setPatientState(patientFrom.getPatientState());
        }
        if (patientFrom.getFirstName() != null) {
            patientTo.setFirstName(patientFrom.getFirstName());
        }
        if (patientFrom.getLastName() != null) {
            patientTo.setLastName(patientFrom.getLastName());
        }
    }

    public void deactivatePatient(String patientId) {
        Patient foundPatient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> {
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Patient for deleting not found");
                        }
                );

        patientRepository.deleteById(foundPatient.getPatientId());
    }

    public Map<JSONObject, List<Order>> getPatientListWithTheirActiveOrders() {
        Map<String, List<Order>> activeOrders =
                orderConsumer.getAllOrdersByState("ACTIVE")
                .stream()
                .collect(Collectors.groupingBy(Order::getPatientId));

        Map<JSONObject, List<Order>> patientsWithTheirOrders = new HashMap<>();
        for (Patient patient : patientRepository.findAll()) {
            if (activeOrders.get(patient.getPatientId()) != null) {
                patientsWithTheirOrders.put(
                        new JSONObject(patient),
                        activeOrders.get(patient.getPatientId())
                );
            }
        }

        return patientsWithTheirOrders;
    }
}
