package com.jmt.restaurant.services;

import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantInfoService {
    private final RestaurantRepository restaurantRepository; // 카운트 할 때 필요



}
