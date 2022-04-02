package com.example.orderservice.controller;

import com.example.orderservice.dto.PatientIdWrapper;
import com.example.orderservice.dto.OrderRequest;
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
        ResponseEntity<Order> orderResponseEntity = new ResponseEntity<>(
                orderService.createOrder(orderRequest),
                HttpStatus.CREATED);

        log.info("Order {} was created", orderRequest);
        return orderResponseEntity;
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId,
                                             @Valid @RequestBody OrderRequest orderRequest) {
        ResponseEntity<Order> orderResponseEntity = new ResponseEntity<>(
                orderService.updateOrder(orderId, orderRequest),
                HttpStatus.OK);

        log.info("Order with orderId {} was updated", orderRequest);
        return orderResponseEntity;
    }

    @DeleteMapping("/decline/{orderId}")
    public ResponseEntity<HttpStatus> declineOrder(@PathVariable String orderId) {
        orderService.declineOrder(orderId);

        log.info("Order with id {} was declined", orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Order>> getOrdersByPatientIdsAndOrderState(
            @RequestBody PatientIdWrapper patientIdsWrapper,
            @RequestParam(name = "orderState") Optional<String> orderState
    ) {

        log.info("All orders with patientIds {} and orderState {} were received",
                patientIdsWrapper.getPatientIds().toString(), orderState);

        return new ResponseEntity<>(
                orderService.getOrdersByPatientIdsAndOrderState(
                        patientIdsWrapper.getPatientIds(), orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }

    @GetMapping(params = {"patientId", "orderState"})
    public ResponseEntity<List<Order>> getOrdersByPatientIdAndOrderState(
            @RequestParam(name = "patientId") String patientId,
            @RequestParam(name = "orderState") Optional<String> orderState
    ) {

        log.info("All orders with patientIds {} and orderState {} were received",
                patientId, orderState);

        return new ResponseEntity<>(
                orderService.getByPatientIdAndOrderState(
                        patientId, orderState.orElse("NOT PROVIDED")),
                HttpStatus.OK);
    }
}
