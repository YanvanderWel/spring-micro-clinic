package com.example.patientservice.service;

import com.example.patientservice.data.PatientState;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.Patient;
import com.example.patientservice.persistence.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository userRepository;

    private PatientService patientService;

    @BeforeEach
    void initUseCase() {
        patientService = new PatientService(userRepository);
    }

    @Test
    void savedUserHasFirstName() {
        PatientRequest request = new PatientRequest(
                "Yan", "Levchenko", PatientState.ACTIVE
        );
        when(userRepository.save(any(Patient.class))).then(returnsFirstArg());

        Patient savedPatient = patientService.createPatient(request);

        assertThat(savedPatient.getFirst_name()).isNotNull();
    }
}