package com.example.patientservice.service.web;

import com.example.patientservice.controller.PatientController;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.patientservice.Utils.asJsonString;
import static com.example.patientservice.service.TestEntityProvider.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
@TestPropertySource(properties = "spring.cloud.config.enabled=false")
@TestPropertySources({
        @TestPropertySource(properties = "spring.cloud.config.enabled=false"),
        @TestPropertySource(properties = "spring.cloud.discovery.enabled=false")
})
public class PatientRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientMapper patientMapper;

    @Test
    public void createPatientAPI() throws Exception {
        when(patientService.createPatient(any())).thenReturn(buildPatient());

        mvc.perform(MockMvcRequestBuilders
                        .post("/patients/create")
                        .content(asJsonString(buildPatientRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").exists());
    }

    @Test
    public void updatePatientAPI() throws Exception {
        Patient patient = buildPatient();
        when(patientService.updatePatient(any(), any())).thenReturn(patient);

        mvc.perform(MockMvcRequestBuilders
                        .put("/patients/update/{id}", patient.getPatientId())
                        .content(asJsonString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.fullName").value(patient.getFullName()));
    }

    @Test
    public void deletePatientAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(
                "/patients/deactivate/{id}", faker.idNumber().valid()))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getPatientListWithTheirActiveOrdersAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/patients/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
