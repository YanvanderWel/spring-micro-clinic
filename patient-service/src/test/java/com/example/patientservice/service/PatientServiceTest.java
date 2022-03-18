package com.example.patientservice.service;

import com.example.patientservice.client.OrderConsumer;
import com.example.patientservice.data.Order;
import com.example.patientservice.data.PatientState;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private OrderConsumer orderConsumer;

    private PatientService patientService;

    private final Faker faker = new Faker(new Locale("en-GB"));

    @BeforeEach
    void initUseCase() {
        patientService = new PatientService(patientRepository, orderConsumer);
    }

    @Test
    void givenPatient_whenSave_thenPatientHasFirstName() {

        Patient patient = Patient.builder()
                .firstName(faker.name().firstName())
                .build();

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());

        Patient savedPatient = patientService.createPatient(patient);

        assertThat(savedPatient.getFirstName()).isNotNull();
    }

    @Test
    void givenPatient_whenUpdate_thenPatientHasAnotherFirstName() {
        String firstName = faker.name().firstName();

        Patient patient = Patient.builder()
                .firstName(firstName)
                .build();
        String updatedFirstName = firstName + "1";

        Patient patient1 = Patient.builder()
                .patientId(patient.getPatientId())
                .firstName(updatedFirstName)
                .build();

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));

        Patient updatedPatient = patientService.updatePatient(patient1);

        assertThat(updatedPatient.getFirstName()).isEqualTo(updatedFirstName);
    }

    @Test
    void givenPatient_whenDeactivate_thenPatientHasInactiveState() {
        String firstName = faker.name().firstName();

        Patient patient = Patient.builder()
                .firstName(firstName)
                .build();

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));

        Patient deactivatedPatient = patientService.deactivatePatient(patient);

        assertThat(deactivatedPatient.getPatientState()).isEqualTo(PatientState.INACTIVE.name());
    }

    @Test
    void givenPatientAndOrderLists_whenMapThem_thenFirstPatientHasOnlyOneOrder() {
        Patient patient = Patient.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientState(PatientState.ACTIVE.name())
                .build();

        List<Order> activeOrders = new ArrayList<>();
        Order order = Order.builder()
                .patientId(patient.getPatientId())
                .orderState("ACTIVE")
                .build();

        activeOrders.add(order);

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        when(orderConsumer.getAllActiveOrders()).thenReturn(activeOrders);

        when(patientRepository.findAll()).thenReturn(patients);

        Map.Entry<JSONObject, List<Order>> entry =
                patientService.getPatientListWithTheirActiveOrders().entrySet().iterator().next();
        List<Order> orders = entry.getValue();

        assertThat(orders.size()).isEqualTo(1);
    }
}