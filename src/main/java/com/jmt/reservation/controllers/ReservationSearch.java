package com.jmt.reservation.controllers;

import lombok.Data;

@Data
public class ReservationSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt; // 검색 조건
    private String skey; // 검색 조건
    
}
