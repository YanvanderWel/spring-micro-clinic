package com.example.patientservice.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients")
public class Patient extends AbstractTimestampEntity {

    @Id
    @GenericGenerator(
            name = "patient_id_gen",
            strategy = "com.example.patientservice.model.generator.PatientIdGenerator"
    )
    @GeneratedValue(generator = "patient_id_gen")
    @Column(name = "patient_id")
    private String patientId;

    @JsonIgnore
    @Column(name = "first_name")
    private String firstName;

    @JsonIgnore
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patient_state")
    private String patientState;

    @JsonGetter(value = "fullName")
    public String getFullName() {
        return (firstName + " " + lastName);
    }

}
