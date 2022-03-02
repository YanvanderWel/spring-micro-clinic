package com.example.patientservice.persistence;

import com.example.patientservice.data.PatientState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.spanner.core.mapping.*;
import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients")
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "patient_id")
    private String patient_id;

    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "create_data_time_gmt")
    private LocalDate create_date_time_gmt;
    @Column(name = "update_data_time_gmt")
    private LocalDate update_date_time_gmt;

    @Column(name = "patient_state")
    private String patient_state;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Order> orders;
}
