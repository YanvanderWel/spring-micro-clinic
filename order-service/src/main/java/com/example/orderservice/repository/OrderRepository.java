package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Order.OrderEntryPK> {
    List<Order> findByPatientIdAndOrderState(String patientId, String orderState);
    List<Order> findByPatientIdInAndOrderState(List<String> patientIds, String orderState);
    Optional<Order> findByOrderId(String orderId);
}
