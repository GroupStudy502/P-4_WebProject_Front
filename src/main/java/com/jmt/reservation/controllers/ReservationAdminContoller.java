package com.jmt.reservation.controllers;

import com.jmt.global.ListData;
import com.jmt.global.rests.JSONData;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.services.ReservationAdminService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation/admin")
public class ReservationAdminContoller {
    private final ReservationAdminService service;
    private final JPAQueryFactory queryFactory;


    @GetMapping("/list")// 목록 조회
    public JSONData getList(ReservationSearch search) {
        ListData<Reservation> data = service.getList(search);

        return new JSONData(data);
    }

    @GetMapping("/list2")// 목록 조회
    public JSONData getList2() {
        List<Reservation> data = service.getList2();

        System.out.println(data);
        return new JSONData(data);
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        Reservation item = service.delete(seq);

        return new JSONData(item);
    }
}