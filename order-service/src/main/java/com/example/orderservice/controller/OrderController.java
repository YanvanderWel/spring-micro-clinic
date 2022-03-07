package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.persistence.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
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

//    @GetMapping("/all/patient_id/{patient_id}")
//    public List<Order> getActiveOrdersByPatientId(@PathVariable String patient_id) {
//        return orderService.getActiveOrdersByPatientId(patient_id);
//    }

    @GetMapping("/all/active")
    public List<Order> getAllActiveOrders() {
        return orderService.getAllActiveOrders();
    }

}
