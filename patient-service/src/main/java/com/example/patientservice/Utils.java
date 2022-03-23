package com.example.patientservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Utils {

    public static Timestamp getTimestampNow() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
