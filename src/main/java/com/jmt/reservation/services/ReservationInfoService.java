package com.jmt.reservation.services;

import com.jmt.global.ListData;
import com.jmt.reservation.controllers.ReservationSearch;
import com.jmt.reservation.entities.QReservation;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.exceptions.ReservationNotFoundException;
import com.jmt.reservation.repositories.ReservationRepository;
import com.jmt.restaurant.entities.Restaurant;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final JPAQueryFactory queryFactory;

    public Reservation get(Long orderNo) {
        Reservation item = reservationRepository.findById(orderNo).orElseThrow(ReservationNotFoundException::new);

        // 추가 데이터 처리
        addInfo(item);

        return item;
    }

    private void addInfo(Reservation item) {
        int persons = Math.max(item.getPersons(), 1);
        int price = item.getPrice();

        int totalPayPrice = price * persons;
        item.setTotalPayPrice(totalPayPrice);
    }

    public ListData<Restaurant> getList(ReservationSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit(); // 한페이지당 보여줄 레코드 갯수
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit; // 레코드 시작 위치

        // 검색 처리 S
        String sopt = search.getSopt(); // 검색 옵션 ALL - 통합 검색
        String skey = search.getSkey(); // 검색 키워드

        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // 검색 처리 E

        return null;
    }
}
