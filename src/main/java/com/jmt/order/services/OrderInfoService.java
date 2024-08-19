package com.jmt.order.services;

import com.jmt.order.entities.OrderInfo;
import com.jmt.order.entities.OrderItem;
import com.jmt.order.repositories.OrderInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;

    public OrderInfo get(Long orderNo) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderNo).orElseThrow(OrderNotFoundException::new);

        addInfo(orderInfo);

        return orderInfo;
    }

    // 추가 처리
    public void addInfo(OrderInfo orderInfo) {
        OrderItem orderItem = orderInfo.getOrderItem();
        int totalPayPrice = orderItem.getPrice() * orderItem.getQty();

        orderInfo.setTotalPayPrice(totalPayPrice);

    }
}