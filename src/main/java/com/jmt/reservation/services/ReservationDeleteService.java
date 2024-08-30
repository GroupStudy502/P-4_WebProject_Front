package com.jmt.reservation.services;

import com.jmt.reservation.entities.Reservation;
import com.jmt.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {
    private final ReservationInfoService infoService;
    private final ReservationRepository repository;

    /**
     * 삭제 기능
     *
     * @param orderNo
     * @return
     */
    public Reservation delete(Long orderNo) {

        Reservation data = infoService.get(orderNo);
        data.setDeletedAt(LocalDateTime.now());
        repository.saveAndFlush(data);

        return data;
    }

}