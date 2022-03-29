package com.example.orderservice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractTimestampEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date_time_gmt")
    private Date createDateTimeGmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date_time_gmt")
    private Date updateDateTimeGmt;

    @PrePersist
    protected void onCreate() {
        updateDateTimeGmt = createDateTimeGmt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDateTimeGmt = new Date();
    }
}

