package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient patientRequestToPatient(PatientRequest patient);
}
