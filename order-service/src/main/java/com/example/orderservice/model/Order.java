package com.example.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@IdClass(Order.OrderEntryPK.class)
public class Order extends AbstractTimestampEntity implements Serializable {

    @Id
    private String patientId;

    @Id
    @GenericGenerator(
            name = "order_id_gen",
            strategy = "com.example.orderservice.model.generator.OrderIdGenerator"
    )
    @GeneratedValue(generator = "order_id_gen")
    private String orderId;

    private String orderComment;

    private String orderState;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderEntryPK implements Serializable {
        private String patientId;
        private String orderId;
    }

    @JsonIgnore
    public OrderEntryPK getId() {
        return new OrderEntryPK(patientId, orderId);
    }

}
