package com.example.orderservice.service;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.orderRequestToOrder(orderRequest);

        return orderRepository.save(order);
    }

    public Order updateOrder(String orderId, OrderRequest orderRequest) {
        Order foundOrder = orderRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Order for updating not found");
                });

        orderMapper.updateOrderFromRequest(foundOrder, orderRequest);

        return orderRepository.save(foundOrder);
    }

    public void declineOrder(String orderId) {
        Order foundOrder = orderRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Order for declining not found");
                });

        orderRepository.deleteById(foundOrder.getId());
    }

    public List<Order> getOrdersByPatientIdsAndOrderState(
            List<String> patientIds, String orderState) {
        List<Order> result = new ArrayList<>();

        for (String patientId : patientIds) {
            result.addAll(orderRepository.findByPatientIdAndOrderState(patientId, orderState));
        }
        return result;
    }

    public List<Order> getByPatientIdAndOrderState(String patientId, String orderState) {
        return orderRepository.findByPatientIdAndOrderState(patientId, orderState);
    }
}
