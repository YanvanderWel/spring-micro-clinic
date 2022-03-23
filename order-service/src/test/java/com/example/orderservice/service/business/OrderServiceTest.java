package com.example.orderservice.service.business;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.example.orderservice.service.BuildObjectMethods.order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void initUseCase() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void givenOrder_whenSave_thenOrderHasOrderComment() {
        Order order = order();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order updatedOrder = orderService.createOrder(order);

        assertThat(updatedOrder.getOrderComment()).isNotNull();
        verify(orderRepository).save(eq(order));
    }

    @Test
    void givenOrder_whenUpdate_thenOrderHasAnotherComment() {

        Order order = order();
        Order order1 = order();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        orderService.updateOrder(order.getId(), order1);

        assertThat(order.getOrderComment()).isEqualTo(order1.getOrderComment());
    }

    @Test
    void givenOrder_whenDecline_thenOrderHasDeclineState() {

        Order order = order();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        orderService.declineOrder(order.getId());

        verify(orderRepository).deleteById(eq(order.getId()));
    }

    @Test
    void getAllActiveOrders() {
        when(orderRepository.findByOrderState(anyString())).thenReturn(List.of(new Order(), new Order()));

        List<Order> allActiveOrders = orderService.getAllOrdersByState(OrderState.ACTIVE.name());

        assertThat(allActiveOrders).hasSize(2);
    }
}