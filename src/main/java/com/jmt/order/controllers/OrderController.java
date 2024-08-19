package com.jmt.order.controllers;

import com.jmt.global.exceptions.ExceptionProcessor;
import com.jmt.order.entities.OrderInfo;
import com.jmt.order.services.OrderPayService;
import com.jmt.order.services.OrderSaveService;
import com.jmt.payment.services.PaymentConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor {
    private final OrderSaveService saveService;
    private final OrderPayService payService;
    private final Utils utils;

    @GetMapping // 주문서 양식
    public String index(@ModelAttribute RequestOrder form) {

        return utils.tpl("order/form");
    }

    @PostMapping
    public String orderSave(@Valid RequestOrder form, Errors errors, Model model) {

        OrderInfo orderInfo = saveService.save(form);

        if (!errors.hasErrors()) {
            PaymentConfig config = payService.getConfig(orderInfo.getOrderNo());
            model.addAttribute("config", config);
            model.addAttribute("orderInfo", orderInfo);
        }


        return utils.tpl("order/form");

    }
}