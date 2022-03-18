package com.example.orderservice.service;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private final Faker faker = new Faker(new Locale("en-GB"));

    @Mock
    OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void initUseCase() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void givenOrder_whenSave_thenOrderHasOrderComment() {
        Order order = Order.builder()
                .orderComment(faker.app().author())
                .build();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order order1 = orderService.createOrder(order);

        assertThat(order1.getOrderComment()).isNotNull();
    }

    @Test
    void givenOrder_whenUpdate_thenOrderHasAnotherComment() {

        Order order = Order.builder()
                .orderComment(faker.name().firstName())
                .build();

        String updatedOrderComment = faker.name().lastName();
        Order order1 = Order.builder()
                .orderComment(updatedOrderComment)
                .build();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        Order updatedOrder = orderService.updateOrder(order1);

        assertThat(updatedOrder.getOrderComment()).isEqualTo(updatedOrderComment);
    }

    @Test
    void givenOrder_whenDecline_thenOrderHasDeclineState() {

        Order order = Order.builder()
                .build();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        Order updatedOrder = orderService.declineOrder(order);

        assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.DECLINED.name());
    }

    @Test
    void getAllActiveOrders() {
        when(orderRepository.findByOrderState(anyString())).thenReturn(List.of(new Order(), new Order()));

        List<Order> allActiveOrders = orderService.getAllOrdersByState(OrderState.ACTIVE.name());

        assertThat(allActiveOrders).hasSize(2);
    }
}