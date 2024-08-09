package com.jmt.restaurant.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenu{
    @Id
    private Long menuId; // 메뉴 ID

    private String menuNm; // 메뉴명

    private String menuDscrn; // 메뉴설명(주재료, 조리법, 소스, 옵션)

    private String menuCtgryLclasNm; // 메뉴카테고리대분류명

    private String menuCtgrySclasNm; // 메뉴카테고리소분류명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rstrId")
    private Restaurant restaurant;
}
