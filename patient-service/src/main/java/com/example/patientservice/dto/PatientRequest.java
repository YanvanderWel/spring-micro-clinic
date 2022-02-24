package com.example.patientservice.dto;

import com.example.patientservice.data.PatientState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    private String firstName;
    private String lastName;
    private PatientState patientState;
}
