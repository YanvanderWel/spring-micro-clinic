package com.example.orderservice.service;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setCreateDateTimeGmt(LocalDate.now());
        order.setUpdateDateTimeGmt(LocalDate.now());

        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        Order.OrderEntryPK orderEntryPK = new Order.OrderEntryPK();
        orderEntryPK.setOrderId(order.getOrderId());
        orderEntryPK.setPatientId(order.getPatientId());

        Optional<Order> foundOrder = Optional.ofNullable(orderRepository
                .findById(orderEntryPK)
                .orElseThrow(NullPointerException::new));

        foundOrder.ifPresent(order_ -> {
            if (order.getOrderComment() != null) {
                order_.setOrderComment(order.getOrderComment());
            }
            if (order.getOrderState() != null) {
                order_.setOrderState(order.getOrderState());
            }
            orderRepository.save(order_);
        });
        return foundOrder.orElseThrow(NullPointerException::new);
    }

    public Order declineOrder(Order order) {
        Order.OrderEntryPK orderEntryPK = new Order.OrderEntryPK();
        orderEntryPK.setOrderId(order.getOrderId());
        orderEntryPK.setPatientId(order.getPatientId());
        Optional<Order> foundOrder = orderRepository.findById(orderEntryPK);

        foundOrder.ifPresent(order_ -> {
            order_.setOrderState(OrderState.DECLINED.name());
            orderRepository.save(order_);
        });
        return foundOrder.orElseThrow(NullPointerException::new);
    }

    public List<Order> getAllOrdersByState(String orderState) {
        try {
            return orderRepository.findByOrderState(OrderState.valueOf(orderState).name());
        }catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
