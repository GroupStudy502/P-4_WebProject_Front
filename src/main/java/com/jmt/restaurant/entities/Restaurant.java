package com.jmt.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    private Long restrId;
    private String rstrNm; // 식당명

    private String rstrRdnmAdr; // 도로명 주소
    private String rstrLnnoAdres; // 구주소
    private Double rstrLa; // 위도
    private Double rstrLo; // 경도
    private Double rstrTelNo; // 전화번호
    private Double dbsnsStatmBzcndNm; //  영업신고증업태명
    private Double bsnsLcncNm; // 영업인허가명
    @Lob
    private String rstrIntrcnCont; // 음식점 소개 내용

    private String areaNm; // 지역명
    private Integer prdlSeatCnt; // 입식좌석수
    private Integer seatCnt; // 좌식좌석수
    private Boolean prkgPosYn; // 주차 가능 여부
    private Boolean wifiOfrYn; // WI-FI 제공 여부
    private Boolean dcrnYn; // 놀이방 유무
    private Boolean petEnternPosblYn; // 반려동물 입장 여부
    private Boolean fgggMenuOfrYn; // 다국어 메뉴판 제공 여부
    private String tlromInfoCn; // 화장실 정보 내용
    private String restdyInfoCn; // 휴무일 정보 내용
    private String bsnsTmCn; // 영업시간 내용
    private Boolean hmdlvSaleYn; // 택배 판매 유무
    private Boolean dsbrCvntlYn; // 배리어프리유무
    private Boolean delvSrivicYn; // 배달서비스유무
    private String rsrvMthdNm; // 예약방식명
    private String onlineRsrvInfoCn; // 온라인 예약 정보 내용
    private String hmpgUrl; // 홈페이지 URL
    private Boolean kioskYn; // 키오스크 유무
    private Boolean mbPmamtYn; // 모바일페이먼트 유무
    private Boolean smorderYn; // 스마트오더 유무
    private String reprsntMenuNm; // 대표메뉴명


    @ToString.Exclude
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<RestaurantImage> images;
}
