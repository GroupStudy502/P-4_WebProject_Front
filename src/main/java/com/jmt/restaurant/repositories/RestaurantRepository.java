package com.jmt.restaurant.repositories;

import com.jmt.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, QuerydslPredicateExecutor<Restaurant> {

    @Query("SELECT r FROM Restaurant r left join r.foods m where concat(r.rstrLnnoAdres, r.rstrRdnmAdr) like :k1 and concat(m.menuNm, r.rstrIntrcnCont, r.rstrNm, r.reprsntMenuNm) like :k2")
    List<Restaurant> findRestaurants(@Param("k1") String key1, @Param("k2")String key2);

    @Query("SELECT count(r) FROM Restaurant r left join r.foods m where concat(r.rstrLnnoAdres, r.rstrRdnmAdr) like :k1 and concat(m.menuNm, r.rstrIntrcnCont, r.rstrNm, r.reprsntMenuNm) like :k2")
    Long getCountBy(@Param("k1") String key1, @Param("k2")String key2);


 }
