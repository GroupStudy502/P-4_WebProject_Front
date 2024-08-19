package com.jmt.order.entities;

import com.jmt.board.entities.BoardData;
import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    private Long seq;

    private String itemName; // 주문 상품명

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardData boardData;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private OrderInfo orderInfo;

    private int price; // 상품 단품 금액

    private int qty; // 주문 수량
}