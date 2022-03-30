package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.model.Patient;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PatientMapper {

    Patient patientRequestToPatient(PatientRequest request);

    void updatePatientFromRequest(@MappingTarget Patient patient, PatientRequest patientRequest);
}
