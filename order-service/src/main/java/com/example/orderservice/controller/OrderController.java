package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("New order registration {}", orderRequest);
        return new ResponseEntity<>(
                orderService.createOrder(orderMapper.orderRequestToOrder(orderRequest)),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{patientId}/{orderId}")
    public ResponseEntity<Order> updateOrder(Order.OrderEntryPK id,
                                             @RequestBody OrderRequest orderRequest) {
        log.info("Order updated {}", orderRequest);
        return new ResponseEntity<>(
                orderService.updateOrder(id, orderMapper.orderRequestToOrder(orderRequest)),
                HttpStatus.OK);
    }

    @DeleteMapping("/decline/{patientId}/{orderId}")
    public ResponseEntity<HttpStatus> declineOrder(Order.OrderEntryPK id) {
        log.info("Order declined with id {}", id);
        orderService.declineOrder(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam Optional<String> orderState) {
        log.info("All {} orders received", orderState.orElse("NOT PROVIDED"));
        return new ResponseEntity<>(
                orderService.getAllOrdersByState(orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }

}
