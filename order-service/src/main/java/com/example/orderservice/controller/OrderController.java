package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("New order registration {}", orderRequest);
        return new ResponseEntity<>(
                orderService.createOrder(orderRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId,
                                             @RequestBody OrderRequest orderRequest) {
        log.info("Order updated {}", orderRequest);
        return new ResponseEntity<>(
                orderService.updateOrder(orderId, orderRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/decline/{orderId}")
    public ResponseEntity<HttpStatus> declineOrder(@PathVariable String orderId) {
        log.info("Order declined with id {}", orderId);
        orderService.declineOrder(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam Optional<String> orderState) {
        log.info("All {} orders received", orderState.orElse("NOT PROVIDED"));
        return new ResponseEntity<>(
                orderService.getAllOrdersByState(orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }
}
