package com.example.patientservice.persistence;

import com.example.patientservice.data.PatientState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @SequenceGenerator(
            name = "patient_id_sequence",
            sequenceName = "patient_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_id_sequence"
    )
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate createDateTimeGmt = LocalDate.now();
    private LocalDate updateDateTimeGmt = LocalDate.now();
    private PatientState patientState;
}
