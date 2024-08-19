package com.jmt.order.services;


import com.jmt.board.entities.BoardData;
import com.jmt.board.services.BoardInfoService;
import com.jmt.member.MemberUtil;
import com.jmt.order.constants.OrderStatus;
import com.jmt.order.controllers.RequestOrder;
import com.jmt.order.entities.OrderInfo;
import com.jmt.order.entities.OrderItem;
import com.jmt.order.repositories.OrderInfoRepository;
import com.jmt.order.repositories.OrderItemRepository;
import com.jmt.payment.constants.PayMethod;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSaveService {
    private final OrderInfoRepository infoRepository;
    private final OrderItemRepository itemRepository;
    private final BoardInfoService boardInfoService;
    private final MemberUtil memberUtil;

    public OrderInfo save(RequestOrder form) {
        // 게시글 조회
        BoardData boardData = boardInfoService.get(form.getBSeq());

        long orderNo = System.currentTimeMillis();

        Long num1 = boardData.getNum1();
        Long num2 = boardData.getNum2();

        int price = num1 == null ? 0 : num1.intValue();
        int qty = num2 == null && num2 < 1L ? 1 : num2.intValue();
        String itemName = boardData.getText1();

        /* 주문서 정보 저장 S */
        OrderInfo orderInfo = new ModelMapper().map(form, OrderInfo.class);
        orderInfo.setPayMethod(PayMethod.valueOf(form.getPayMethod()));
        orderInfo.setOrderNo(orderNo);
        orderInfo.setMember(memberUtil.getMember());
        orderInfo.setStatus(OrderStatus.START);

        infoRepository.saveAndFlush(orderInfo);
        /* 주문서 정보 저장 E */

        /* 주문 상품 정보 저장 S */
        OrderItem orderItem = OrderItem.builder()
                .orderInfo(orderInfo)
                .itemName(itemName)
                .price(price)
                .qty(qty)
                .boardData(boardData)
                .build();
        itemRepository.saveAndFlush(orderItem);
        /* 주문 상품 정보 저장 E */

        return orderInfo;
    }
}