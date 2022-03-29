package com.example.patientservice.service;

import com.example.patientservice.data.PatientState;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.model.Patient;
import com.github.javafaker.Faker;

import java.sql.Timestamp;
import java.util.Locale;

import static com.example.patientservice.Utils.getTimestampNow;

public class TestEntityProvider {

    public static final Faker faker = new Faker(new Locale("en-GB"));

    public static Patient patient() {
        return Patient.builder()
                .patientId(faker.idNumber().valid())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientState(PatientState.ACTIVE.name())
                .build();
    }

    public static PatientRequest patientRequest() {
        return PatientRequest.builder()
                .patientId(faker.idNumber().valid())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientState(PatientState.ACTIVE)
                .build();
    }
}
