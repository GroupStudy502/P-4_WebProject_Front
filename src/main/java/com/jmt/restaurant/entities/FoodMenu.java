package com.jmt.restaurant.entities;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FoodMenu {

    @Id
    private Long menuId; //메뉴 ID

    private String menuNm; //메뉴명

    private Integer menuPrice; // 메뉴 가격

    private Boolean spcltMenuYn; //지역특산메뉴여부

    private String spcltMenuNm; // 지역특산메뉴명

    private String menuDscrn; //메뉴설명(주재료,조리법,소스,옵션)

    private String menuCtgryLclasNm; // 메뉴카테고리대분류명

    private String menuCtgrySclasNm; // 메뉴 카테고리 소분류명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rstrId")
    private Restaurant restaurant;
}
