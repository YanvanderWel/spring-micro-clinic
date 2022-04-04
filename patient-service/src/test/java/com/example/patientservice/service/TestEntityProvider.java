package com.example.patientservice.service;

import com.example.patientservice.data.PatientState;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.model.Patient;
import com.github.javafaker.Faker;

import java.util.Locale;

public class TestEntityProvider {

    public static final Faker faker = new Faker(new Locale("en-GB"));

    public static Patient buildPatient() {
        return Patient.builder()
                .patientId(faker.idNumber().valid())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientState(PatientState.ACTIVE.name())
                .build();
    }

    public static PatientRequest buildPatientRequest() {
        return PatientRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientState(PatientState.ACTIVE)
                .build();
    }
}
