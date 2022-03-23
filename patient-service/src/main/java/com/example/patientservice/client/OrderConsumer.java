package com.example.patientservice.client;

import com.example.patientservice.data.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderConsumer {

    @GetMapping("/orders/all?orderState=ACTIVE")
    List<Order> getAllActiveOrders();
}
