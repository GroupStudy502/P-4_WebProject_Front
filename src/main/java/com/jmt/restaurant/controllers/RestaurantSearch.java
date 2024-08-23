package com.jmt.restaurant.controllers;

import com.jmt.global.CommonSearch;
import lombok.Data;

@Data
public class RestaurantSearch extends CommonSearch {
    private String areaNm; // 지역명(서울특별시+구)
    private String dbsnsStatmBzcndNm;  // 업종명
}
