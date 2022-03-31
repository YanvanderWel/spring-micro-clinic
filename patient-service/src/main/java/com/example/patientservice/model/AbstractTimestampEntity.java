package com.example.patientservice.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class AbstractTimestampEntity implements Serializable {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date_time_gmt")
    private Date createDateTimeGmt;

    @UpdateTimestamp
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

