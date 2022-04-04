package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    Order orderRequestToOrder(OrderRequest request);

    void updateOrderFromRequest(@MappingTarget Order order, OrderRequest orderRequest);
}
