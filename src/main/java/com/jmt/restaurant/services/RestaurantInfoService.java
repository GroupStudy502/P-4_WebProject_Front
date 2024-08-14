package com.jmt.restaurant.services;

import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.restaurant.controllers.RestaurantSearch;
import com.jmt.restaurant.entities.QRestaurant;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.exceptions.RestaurantNotFoundException;
import com.jmt.restaurant.repositories.RestaurantRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantInfoService {
    private final HttpServletRequest request; // 검색어 반영된 쿼리스트링 값
    private final RestaurantRepository repository; // 카운트 할 때 필요
    private final JPAQueryFactory queryFactory;

    public ListData<Restaurant> getList(RestaurantSearch search) {
        int page = Math.max(search.getPage(), 1); // 페이지가 0이거나 음수이면 1이 나오도록 설정
        int limit = search.getLimit(); // 한페이지당 보여줄 레코드 개수
        limit = limit < 1 ? 20 : limit;
        int offset = (page -1) * limit; // 레코드 시작 위치 구하기

        // 검색 처리 B
        String sopt = search.getSopt(); // 검색 옵션 All - 통합 검색
        String skey = search.getSkey();  // 검색 키워드를 통한 검색 ex) 음식분류, 옵션 검색

        QRestaurant restaurant = QRestaurant.restaurant;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // 검색 처리 D

        // 검색 데이터 처리
        List<Restaurant> items = queryFactory.selectFrom(restaurant)
                .leftJoin(restaurant.images)
                .fetchJoin()
                .where(andBuilder) // 검색 조건 후에 추가
                .offset(offset)
                .limit(limit)
                .orderBy(restaurant.createdAt.desc()) // 정렬 조건 후에 추가
                .fetch();

        // 페이징 데이터
        long total = repository.count(andBuilder); // 조회된 전체 갯수

        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    /**
     * 식당 개별 정보 조회
     * @param rstrId
     * @return
     */
    public Restaurant get(Long rstrId) {
        // 2차 가공 필요
        Restaurant item = repository.findById(rstrId).orElseThrow(RestaurantNotFoundException::new);

        // 식당 이미지 바로 가져오기

        // 추가 데이터 처리 -> 리뷰

        return item;
    }


}