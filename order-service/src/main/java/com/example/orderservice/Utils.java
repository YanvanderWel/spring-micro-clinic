package com.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@UtilityClass
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
