package com.example.patientservice.service;

import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.data.Order;
import com.example.patientservice.data.PatientState;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.OrderConsumer;
import com.example.patientservice.persistence.Patient;
import com.example.patientservice.persistence.PatientRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private OrderConsumer orderConsumer;

    private PatientService patientService;

    @BeforeEach
    void initUseCase() {
        patientService = new PatientService(patientRepository, orderConsumer);
    }

    @Test
    void givenPatient_whenSave_PatientHasFirstName() {
        Patient patient = new Patient(
                "1", "Yan",
                "Levchenko", PatientState.ACTIVE
        );

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());

        Patient savedPatient = patientService.createPatient(patient);

        assertThat(savedPatient.getFirstName()).isNotNull();
    }

    @Test
    void givenPatientAndOrderList_whenMapThem_thenFirstPatientHasOnlyOneOrder() {
        List<Order> activeOrders = new ArrayList<>();
        activeOrders.add(
                new Order(
                        "1", "1", LocalDate.now(),
                        LocalDate.now(), "test comment", "ACTIVE"
                )
        );

        List<Patient> patients = new ArrayList<>();
        patients.add(
                new Patient(
                        "1", "Yan", "Levchenko",
                        LocalDate.now(), LocalDate.now(), PatientState.ACTIVE
                )
        );

        when(orderConsumer.getAllActiveOrders()).thenReturn(activeOrders);

        when(patientRepository.findAll()).thenReturn(patients);

        Map.Entry<JSONObject, List<Order>> entry =
                patientService.getPatientListWithTheirActiveOrders().entrySet().iterator().next();
        List<Order> orders = entry.getValue();

        assertThat(orders.size()).isEqualTo(1);

    }
}