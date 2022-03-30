package com.example.patientservice.client;

import com.example.patientservice.data.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderConsumer {

    @GetMapping("/orders")
    List<Order> getOrdersByPatientIdsAndPatientState(
            @RequestParam("patientIds") String[] patientIds,
            @RequestParam("orderState") String orderState
    );
}
