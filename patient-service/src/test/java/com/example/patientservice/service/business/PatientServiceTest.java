package com.example.patientservice.service.business;

import com.example.patientservice.client.OrderConsumer;
import com.example.patientservice.data.Order;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.example.patientservice.service.BuildObjectMethods.patient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

        Patient patient = patient();

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());

        Patient savedPatient = patientService.createPatient(patient);

        assertThat(savedPatient.getFirstName()).isNotNull();
        verify(patientRepository).save(eq(savedPatient));
    }

    @Test
    void givenPatient_whenUpdate_thenPatientHasAnotherFirstName() {
        Patient patient = patient();
        Patient patient1 = patient();

        when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));

        patientService.updatePatient(patient.getPatientId(), patient1);

        assertThat(patient.getFirstName()).isEqualTo(patient1.getFirstName());
    }

    @Test
    void givenPatient_whenDeactivate_thenPatientHasInactiveState() {
        Patient patient = patient();

        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));

        patientService.deactivatePatient(patient.getPatientId());

        verify(patientRepository).deleteById(eq(patient.getPatientId()));
    }

    @Test
    void givenPatientAndOrderLists_whenMapThem_thenFirstPatientHasOnlyOneOrder() {
        Patient patient = patient();

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