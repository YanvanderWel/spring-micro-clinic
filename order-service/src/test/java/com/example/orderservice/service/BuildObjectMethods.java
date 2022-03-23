package com.example.orderservice.service;

import com.example.orderservice.data.OrderState;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import com.github.javafaker.Faker;

import java.util.Locale;

import static com.example.orderservice.Utils.getTimestampNow;


public class BuildObjectMethods {

    private static final Faker faker = new Faker(new Locale("en-GB"));

    public static Order order() {
        return Order.builder()
                .patientId(faker.idNumber().valid())
                .orderId(faker.idNumber().invalid())
                .orderComment(faker.app().name())
                .orderState(OrderState.ACTIVE.name())
                .createDateTimeGmt(getTimestampNow())
                .updateDateTimeGmt(getTimestampNow())
                .build();
    }

    public static OrderRequest orderRequest() {
        return OrderRequest.builder()
                .patientId(faker.idNumber().valid())
                .orderId(faker.idNumber().invalid())
                .orderComment(faker.app().name())
                .orderState(OrderState.ACTIVE)
                .createDateTimeGmt(getTimestampNow())
                .updateDateTimeGmt(getTimestampNow())
                .build();
    }
}
