package com.obss_final_project.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {



    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedDate;

    @PrePersist
    protected void prePersist() {
        setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    protected void preUpdate() {
        setUpdatedDate(LocalDateTime.now());
    }

}
