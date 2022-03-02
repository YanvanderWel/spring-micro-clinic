package com.example.patientservice.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.NotMapped;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "orders")
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {
    @EmbeddedId
    private OrderId order_id;

    @Column(name = "create_data_time_gmt")
    private LocalDate create_date_time_gmt;
    @Column(name = "update_data_time_gmt")
    private LocalDate update_date_time_gmt;

    @Column(name = "order_comment")
    private String order_comment;

    @Column
    private String state;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Embeddable
    public static class OrderId implements Serializable {

        @Column(name = "patient_id")
        private String patient_id;

        @Column(name = "order_id")
        private String order_id;
    }
}
