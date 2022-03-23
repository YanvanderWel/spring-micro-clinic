package com.example.orderservice.dto;

import com.example.orderservice.data.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String patientId;
    private String orderId;
    private String orderComment;
    private Timestamp createDateTimeGmt;
    private Timestamp updateDateTimeGmt;
    private OrderState orderState;
}
