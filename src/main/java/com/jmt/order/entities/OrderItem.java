package com.jmt.order.entities;

import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;
}
