package com.example.patientservice.persistence;

import com.example.patientservice.data.PatientState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients")
@Table(name = "patients")
public class Patient implements Serializable {

    @Id
    @Column(name = "patient_id")
    @JsonProperty("patient_id")
    private String patientId;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "create_data_time_gmt")
    @JsonProperty("create_data_time_gmt")
    private LocalDate createDateTimeGmt;

    @Column(name = "update_data_time_gmt")
    @JsonProperty("update_data_time_gmt")
    private LocalDate updateDateTimeGmt;

    @Column(name = "patient_state")
    @JsonProperty("patient_state")
    private PatientState patientState;

    public Patient(String patientId, String firstName, String lastName, PatientState patientState) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientState = patientState;
    }
}
