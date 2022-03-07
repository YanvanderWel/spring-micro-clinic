package com.example.orderservice.service;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.persistence.Order;
import com.example.orderservice.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = Order.builder()
                .order_id(UUID.randomUUID().toString())
                .patient_id(request.getPatient_id())
//                .order_id(new Order.OrderId(request.getPatient_id(), UUID.randomUUID().toString()))
                .order_comment(request.getOrder_comment())
                .create_date_time_gmt(LocalDate.now())
                .update_date_time_gmt(LocalDate.now())
                .build();

        return orderRepository.save(order);
    }

    public List<Order> getAllActiveOrders() {
        return orderRepository.findByOrderState(OrderState.ACTIVE.name());
    }
}
