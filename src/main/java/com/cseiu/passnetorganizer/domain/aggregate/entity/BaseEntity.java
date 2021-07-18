package com.cseiu.passnetorganizer.domain.aggregate.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(name = "created_time")
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "update_time")
    protected Instant updatedAt;
}
