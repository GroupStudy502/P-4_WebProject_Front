package com.jmt.restaurant.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

@Data
public class RestaurantSearch extends CommonSearch {
    private String areaNm; // areaNm - 지역명(서울특별시+구)
    private String dbsnsStatmBzcndNm;  // 업종명

    private String sido; // 현재 위치 시도
    private String sigungu; // 현재 위치 시군구
    private String dong; // 현재 위치 행정동

    public String _toString() {
        return toString() + "_" + super.toString();
    }

}
