package com.example.orderservice.dto;

import com.example.orderservice.data.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Patient id cannot be null")
    private String patientId;

    @NotNull
    @Size(min = 10, max = 200, message
            = "Comment must be between 10 and 200 characters")
    private String orderComment;

    @NotNull(message = "Order state cannot be null")
    private OrderState orderState;
}
