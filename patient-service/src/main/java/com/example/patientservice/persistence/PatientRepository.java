package com.example.patientservice.persistence;

import com.example.patientservice.persistence.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
