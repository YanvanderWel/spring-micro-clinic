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

import java.sql.Timestamp;
import java.util.List;

import static com.example.orderservice.Utils.getTimestampNow;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.orderRequestToOrder(orderRequest);
        Timestamp now = getTimestampNow();
        order.setCreateDateTimeGmt(now);
        order.setUpdateDateTimeGmt(now);

        return orderRepository.save(order);
    }

    public Order updateOrder(String orderId, OrderRequest orderRequest) {
        Order order = orderMapper.orderRequestToOrder(orderRequest);
        Order foundOrder = orderRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Order for updating not found");
                });

        updateOrder(order, foundOrder);

        return orderRepository.save(foundOrder);
    }

    private void updateOrder(Order order, Order foundOrder) {
        if (order.getOrderComment() != null) {
            foundOrder.setOrderComment(order.getOrderComment());
        }
        if (order.getOrderState() != null) {
            foundOrder.setOrderState(order.getOrderState());
        }
        foundOrder.setUpdateDateTimeGmt(getTimestampNow());
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

    public List<Order> getAllOrdersByState(String orderState) {
        return orderRepository.findByOrderState(OrderState.valueOf(orderState).name());
    }
}
