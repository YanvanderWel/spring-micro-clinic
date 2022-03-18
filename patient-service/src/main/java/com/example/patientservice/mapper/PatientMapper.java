package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.model.Patient;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PatientMapper {

    Patient patientRequestToPatient(PatientRequest request);
}
