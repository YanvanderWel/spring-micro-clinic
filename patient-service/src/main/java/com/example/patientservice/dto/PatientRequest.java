package com.example.patientservice.dto;

import com.example.patientservice.data.PatientState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate createDateTimeGmt;
    private LocalDate updateDateTimeGmt = LocalDate.now();
    private PatientState patientState;
}
