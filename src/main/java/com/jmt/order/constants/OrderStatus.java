package com.jmt.order.constants;

import java.util.List;

public enum OrderStatus {
    START("예약접수중"), // 예약 작성 시작
    APPLY("예약접수"), // 예약 접수
    INCASH("입금확인"), // 입금 확인
    COMPLETE("예약완료"), // 예약 완료
    CANCEL("입금전취소"), // 입금전 취소
    REFUND("환불"); // 환불

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public static List<String[]> getList() {
        return List.of(
          new String[] {START.name(), START.title},
          new String[] {APPLY.name(), APPLY.title},
          new String[] {INCASH.name(), INCASH.title},
          new String[] {COMPLETE.name(), COMPLETE.title},
          new String[] {CANCEL.name(), CANCEL.title},
          new String[] {REFUND.name(), REFUND.title}
        );
    }
}
