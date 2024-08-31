package com.jmt.reservation.controllers;

import com.jmt.reservation.constants.ReservationStatus;
import lombok.Data;

@Data
public class RequestReservationStatus {
    private Long orderNo;
    private ReservationStatus status;
}
