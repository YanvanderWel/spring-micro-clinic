package com.example.patientservice.client;

import com.example.patientservice.dto.PatientIdWrapper;
import com.example.patientservice.data.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderConsumer {

    @PostMapping("/orders")
    List<Order> getOrdersByPatientIdsAndPatientState(
            @RequestBody PatientIdWrapper patientIds,
            @RequestParam("orderState") String orderState
    );
}
