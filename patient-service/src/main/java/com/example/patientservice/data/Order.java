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

    private String patientId;

    private String orderId;

    private LocalDate createDateTimeGmt;

    private LocalDate updateDateTimeGmt;

    private String orderComment;

    private String orderState;

}
