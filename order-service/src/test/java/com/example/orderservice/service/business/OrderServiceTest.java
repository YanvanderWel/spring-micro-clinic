package com.example.orderservice.service.business;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.mapper.OrderMapperImpl;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static com.example.orderservice.service.TestEntityProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper = new OrderMapperImpl();

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void initUseCase() {
        orderService = new OrderService(orderRepository, orderMapper);
    }

    @Test
    void givenOrder_whenSave_thenOrderHasOrderComment() {
        OrderRequest orderRequest = buildOrderRequest();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order updatedOrder = orderService.createOrder(orderRequest);

        assertThat(updatedOrder.getOrderComment()).isNotNull();
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void givenOrder_whenUpdate_thenOrderHasAnotherComment() {
        Order order = buildOrder();
        OrderRequest orderRequest = buildOrderRequest();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(orderRepository.findByOrderId(any())).thenReturn(Optional.of(order));

        orderService.updateOrder(order.getOrderId(), orderRequest);

        assertThat(order.getOrderComment()).isEqualTo(orderRequest.getOrderComment());
    }

    @Test
    void givenOrder_whenDecline_thenOrderHasDeclineState() {
        Order order = buildOrder();

        when(orderRepository.findByOrderId(any())).thenReturn(Optional.of(order));

        orderService.declineOrder(order.getOrderId());

        verify(orderRepository).deleteById(eq(order.getId()));
    }

    @Test
    void getOrdersByPatientIdsAndPatientState() {
        when(orderRepository.findByPatientIdAndOrderState(anyString(), anyString()))
                .thenReturn(List.of(new Order(), new Order()));

        List<Order> allActiveOrders = orderService
                .getOrdersByPatientIdsAndOrderState(List.of("1", "2"), faker.idNumber().toString());

        assertThat(allActiveOrders).hasSize(2);
    }
}