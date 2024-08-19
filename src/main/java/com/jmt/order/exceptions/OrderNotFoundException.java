package com.jmt.order.exceptions;

import com.jmt.global.exceptions.script.AlertBackException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AlertBackException {
    public OrderNotFoundException() {
        super("NotFound.order", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}