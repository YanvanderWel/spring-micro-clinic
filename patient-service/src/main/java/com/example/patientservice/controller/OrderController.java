package com.example.patientservice.controller;

import com.example.patientservice.dto.OrderRequest;
import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.persistence.Order;
import com.example.patientservice.service.OrderService;
import com.example.patientservice.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("New order registration {}", orderRequest);
        orderService.createOrder(orderRequest);
    }
}
