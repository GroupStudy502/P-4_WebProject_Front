package com.jmt.reservation.services;

import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.reservation.controllers.ReservationSearch;
import com.jmt.reservation.entities.QReservation;
import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.repositories.ReservationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminService {
    private final ReservationRepository repository;
    private final HttpServletRequest request;
    private final JPAQueryFactory queryFactory;

    public ListData<Reservation> getList(ReservationSearch search) {

        int page = Math.max(search.getPage(),1);
        int limit = search.getLimit();
        if(limit < 10) limit = 10;
        int offset = (page - 1) * limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder();

        sopt = sopt != null && Strings.hasText(sopt.trim()) ? sopt.trim() : "ALL";
        if (skey != null && StringUtils.hasText(skey.trim())) {
            skey = skey.trim();
            BooleanExpression condition = null;

            BooleanBuilder orBuilder = new BooleanBuilder();

            /* 이름 검색 S */
            BooleanBuilder nameCondition = new BooleanBuilder();
            nameCondition.or(reservation.name.contains(skey));
            if (reservation.member != null) {
                nameCondition.or(reservation.member.userName.contains(skey));
            }
            /* 이름 검색 E */

            if (sopt.equals("ALL")) { // 통합 검색
                orBuilder.or(reservation.rName.concat(reservation.name).concat(reservation.email)
                                .contains(skey))
                        .or(nameCondition);

            } else if (sopt.equals("NAME")) { // 제목 검색
                condition = reservation.name.contains(skey);
            } else if (sopt.equals("EMAIL")) { // 내용 검색
                condition = reservation.email.contains(skey);
            } else if (sopt.contains("RNAME")) {
                condition = reservation.rName.contains(skey);
            }

            if (condition != null) andBuilder.and(condition);
            andBuilder.and(orBuilder);
        }

        String sort = search.getSort();

        PathBuilder<Reservation> pathBuilder = new PathBuilder<>(Reservation.class, "reservation");
        OrderSpecifier orderSpecifier = null;
        Order order = Order.DESC;
        if (sort != null && StringUtils.hasText(sort.trim())) {
            // 정렬항목_방향   예) viewCount_DESC -> 조회수가 많은 순으로 정렬
            String[] _sort = sort.split("_");
            if (_sort[1].toUpperCase().equals("ASC")) {
                order = Order.ASC;
            }

            orderSpecifier = new OrderSpecifier(order, pathBuilder.get(_sort[0]));
        }

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (orderSpecifier != null) {
            orderSpecifiers.add(orderSpecifier);
        }
        orderSpecifiers.add(reservation.orderNo.desc());
        //orderSpecifiers.add(reservation.createdAt.desc());
        List<Reservation> items = queryFactory
                .selectFrom(reservation)
                .leftJoin(reservation.restaurant)
                .fetchJoin()
                .leftJoin(reservation.member)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .offset(offset)
                .limit(limit)
                .fetch();



        // 전체 게시글 갯수
        long total = repository.count(andBuilder);

        // 페이징 처리
        int ranges = 10;

        Pagination pagination = new Pagination(page, (int)total, ranges, limit, request);

        return new ListData<>(items, pagination);
    }

    public List<Reservation> getList2() {
        return repository.findAll();
    }

    @Transactional
    public Reservation delete(Long seq) {
        Reservation data = repository.findById(seq).orElse(null);
        if (data == null) {
            return null;
        }
        repository.delete(data);
        repository.flush();

        return data;
    }
}
