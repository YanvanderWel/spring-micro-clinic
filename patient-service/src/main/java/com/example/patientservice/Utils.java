package com.example.patientservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@UtilityClass
public class Utils {

    public static Timestamp getTimestampNow() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    @SneakyThrows
    public static String asJsonString(final Object obj) {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
