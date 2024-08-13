package com.jmt.restaurant.services;

import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantInfoService {
    private final RestaurantRepository restaurantRepository;


}
