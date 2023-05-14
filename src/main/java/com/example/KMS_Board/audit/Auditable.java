package com.example.KMS_Board.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @Getter
    @Setter
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt = LocalDateTime.now();
}