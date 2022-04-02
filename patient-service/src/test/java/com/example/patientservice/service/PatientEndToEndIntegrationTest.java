package com.example.patientservice.service;

import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.google.cloud.NoCredentials;
import com.google.cloud.spanner.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.SpannerEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.patientservice.Utils.asJsonString;
import static com.example.patientservice.service.TestEntityProvider.buildPatient;
import static com.example.patientservice.service.TestEntityProvider.buildPatientRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.discovery.enabled=false"
},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = {IntegrationTestInitializer.class}
)
public class PatientEndToEndIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    public void createPatientReturnsHttpStatusCreated() throws Exception {
        Patient patient = buildPatient();

        patientRepository.save(patient);
        long countBefore = patientRepository.count();
        assertThat(countBefore).isEqualTo(1L);

        this.mockMvc.perform(
                        post("/patients/create")
                                .content(asJsonString(buildPatientRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        patientRepository.save(patient);
        long countAfter = patientRepository.count();
        assertThat(countAfter).isEqualTo(3);
    }

    @Test
    public void updatePatientReturnsHttpStatusOk() throws Exception {
        Patient patient = buildPatient();
        Patient savedPatient = patientRepository.save(patient);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/patients/update/{id}", savedPatient.getPatientId())
                        .content(asJsonString(buildPatientRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        patientRepository.deleteById(savedPatient.getPatientId());
    }

    @Test
    public void deletePatientReturnsHttpStatusAccept() throws Exception {
        Patient patient = buildPatient();
        Patient savedPatient = patientRepository.save(patient);

        mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/patients/deactivate/{id}", savedPatient.getPatientId()))
                .andExpect(status().isNoContent());
    }

}