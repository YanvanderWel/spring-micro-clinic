package com.example.patientservice.dto;

import com.example.patientservice.data.PatientState;
import lombok.Data;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private PatientState patientState;
}
