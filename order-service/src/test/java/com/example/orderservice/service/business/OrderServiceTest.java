package com.example.orderservice.service.business;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.mapper.OrderMapperImpl;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.Optional;

import static com.example.orderservice.service.TestEntityProvider.order;
import static com.example.orderservice.service.TestEntityProvider.orderRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
        OrderMapperImpl.class,
})
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
        OrderRequest orderRequest = orderRequest();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order updatedOrder = orderService.createOrder(orderRequest);

        assertThat(updatedOrder.getOrderComment()).isNotNull();
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void givenOrder_whenUpdate_thenOrderHasAnotherComment() {
        Order order = order();
        OrderRequest orderRequest = orderRequest();

        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(orderRepository.findByOrderId(any())).thenReturn(Optional.of(order));

        orderService.updateOrder(order.getOrderId(), orderRequest);

        assertThat(order.getOrderComment()).isEqualTo(orderRequest.getOrderComment());
    }

    @Test
    void givenOrder_whenDecline_thenOrderHasDeclineState() {
        Order order = order();

        when(orderRepository.findByOrderId(any())).thenReturn(Optional.of(order));

        orderService.declineOrder(order.getOrderId());

        verify(orderRepository).deleteById(eq(order.getId()));
    }

    @Test
    void getAllActiveOrders() {
        when(orderRepository.findByOrderState(anyString())).thenReturn(List.of(new Order(), new Order()));

        List<Order> allActiveOrders = orderService.getAllOrdersByState(OrderState.ACTIVE.name());

        assertThat(allActiveOrders).hasSize(2);
    }
}