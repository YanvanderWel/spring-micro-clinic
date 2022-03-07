package com.example.patientservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {

    private String patient_id;

    private String order_id;

    private LocalDate create_date_time_gmt;

    private LocalDate update_date_time_gmt;

    private String order_comment;

    private String orderState;

}
