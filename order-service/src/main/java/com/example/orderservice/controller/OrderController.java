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
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("Order {} was created", orderRequest);
        return new ResponseEntity<>(
                orderService.createOrder(orderRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId,
                                             @Valid @RequestBody OrderRequest orderRequest) {
        log.info("Order with orderId {} was updated", orderRequest);
        return new ResponseEntity<>(
                orderService.updateOrder(orderId, orderRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/decline/{orderId}")
    public ResponseEntity<HttpStatus> declineOrder(@PathVariable String orderId) {
        log.info("Order with id {} was declined", orderId);
        orderService.declineOrder(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = {"patientIds", "orderState"})
    public ResponseEntity<List<Order>> getOrdersByPatientIdsAndOrderState(
            @RequestParam(name = "patientIds") List<String> patientIds,
            @RequestParam(name = "orderState") Optional<String> orderState
    ) {

        log.info("All orders with patientIds {} and orderState {} were received",
                patientIds.toString(), orderState);

        return new ResponseEntity<>(
                orderService.getOrdersByPatientIdsAndOrderState(
                        patientIds, orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }

    @GetMapping(params = {"patientId", "orderState"})
    public ResponseEntity<List<Order>> getOrdersByPatientIdAndOrderState(
            @RequestParam(name = "patientId") String patientIds,
            @RequestParam(name = "orderState") Optional<String> orderState
    ) {

        log.info("All orders with patientIds {} and orderState {} were received",
                patientIds, orderState);

        return new ResponseEntity<>(
                orderService.getByPatientIdAndOrderState(
                        patientIds, orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }
}
