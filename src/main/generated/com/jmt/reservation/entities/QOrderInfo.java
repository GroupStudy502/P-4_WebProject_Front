package com.jmt.reservation.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.jmt.reservation.constants.ReservationStatus;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderInfo is a Querydsl query type for OrderInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderInfo extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -1179444551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderInfo orderInfo = new QOrderInfo("orderInfo");

    public final com.jmt.global.entities.QBaseEntity _super = new com.jmt.global.entities.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final com.jmt.member.entities.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath orderEmail = createString("orderEmail");

    public final QOrderItem orderItem;

    public final StringPath orderMobile = createString("orderMobile");

    public final StringPath orderName = createString("orderName");

    public final NumberPath<Long> orderNo = createNumber("orderNo", Long.class);

    public final StringPath payBankAccount = createString("payBankAccount");

    public final StringPath payBankName = createString("payBankName");

    public final StringPath payLog = createString("payLog");

    public final EnumPath<com.jmt.payment.constants.PayMethod> payMethod = createEnum("payMethod", com.jmt.payment.constants.PayMethod.class);

    public final StringPath payTid = createString("payTid");

    public final StringPath receiverName = createString("receiverName");

    public final EnumPath<ReservationStatus> status = createEnum("status", ReservationStatus.class);

    public QOrderInfo(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QOrderInfo(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderInfo(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QOrderInfo(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.jmt.member.entities.QMember(forProperty("member")) : null;
        this.orderItem = inits.isInitialized("orderItem") ? new QOrderItem(forProperty("orderItem"), inits.get("orderItem")) : null;
    }

}

