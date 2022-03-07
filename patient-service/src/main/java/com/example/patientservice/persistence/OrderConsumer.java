package com.example.patientservice.persistence;

import com.example.patientservice.data.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderConsumer {

    @GetMapping("/order/all/active")
    List<Order> getAllActiveOrders();
}
