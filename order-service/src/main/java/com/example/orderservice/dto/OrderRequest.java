package com.example.orderservice.dto;

import com.example.orderservice.data.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String patientId;
    private String orderId;
    private String orderComment;
    private LocalDate createDateTimeGmt;
    private LocalDate updateDateTimeGmt;
    private OrderState orderState;
}
