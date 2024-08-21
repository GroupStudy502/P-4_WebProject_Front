package com.jmt.reservation.validators;

import com.jmt.reservation.controllers.RequestReservation;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationValidator implements Validator {

    private final RestaurantInfoService infoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestReservation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestReservation form = (RequestReservation) target;

        Long rstrId = form.getRstrId(); // 식당 등록 번호
        Restaurant restaurant= infoService.get(rstrId);

        LocalDate rDate = form.getRDate(); // 예약일
        LocalTime rTime = form.getRTime(); // 예약 시간

        // 예약일 검증 S
        List<LocalDate> availableDates = restaurant.getAvailableDates();
        if (availableDates == null || availableDates.isEmpty() || !availableDates.contains(rDate)) {
            errors.reject("NotAvailable.restaurant");
        }
        // 예약일 검증 E

    }
}
