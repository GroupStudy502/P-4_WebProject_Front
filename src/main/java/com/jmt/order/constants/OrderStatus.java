package com.jmt.order.constants;

public enum OrderStatus {
    START, // 예약 작성 시작
    APPLY, // 예약 접수
    INCASH, // 입금 확인
    COMPLETE, // 예약 완료
    CANCEL, // 입금전 취소
    REFUND, // 환불

}
