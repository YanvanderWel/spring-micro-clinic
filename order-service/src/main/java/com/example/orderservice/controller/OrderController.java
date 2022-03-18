package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("New order registration {}", orderRequest);
        orderService.createOrder(orderMapper.orderRequestToOrder(orderRequest));
    }

    @PutMapping("/update")
    public void updateOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Order updated {}", orderRequest);
        orderService.updateOrder(orderMapper.orderRequestToOrder(orderRequest));
    }

    @PutMapping ("/decline")
    public void declineOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Order declined {}", orderRequest);
        orderService.declineOrder(orderMapper.orderRequestToOrder(orderRequest));
    }

    @GetMapping("/all")
    public List<Order> getAllOrders(@RequestParam Optional<String> orderState) {
        log.info("All {} orders received", orderState.orElse("NOT PROVIDED"));
        return orderService.getAllOrdersByState(orderState.orElse("NOT PROVIDED"));
    }

}
