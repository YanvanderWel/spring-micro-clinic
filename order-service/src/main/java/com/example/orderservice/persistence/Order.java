package com.example.orderservice.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "orders")
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {

    @PrimaryKey
    private String patient_id;

    @Id
    @PrimaryKey(keyOrder = 2)
    private String order_id;

    @Column(name = "create_data_time_gmt")
    private LocalDate create_date_time_gmt;
    @Column(name = "update_data_time_gmt")
    private LocalDate update_date_time_gmt;

    @Column(name = "order_comment")
    private String order_comment;

    @Column(name = "order_state")
    private String orderState;
}
