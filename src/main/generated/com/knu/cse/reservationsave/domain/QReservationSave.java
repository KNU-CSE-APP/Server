package com.knu.cse.reservationsave.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationSave is a Querydsl query type for ReservationSave
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationSave extends EntityPathBase<ReservationSave> {

    private static final long serialVersionUID = -888916858L;

    public static final QReservationSave reservationSave = new QReservationSave("reservationSave");

    public final EnumPath<com.knu.cse.classroom.domain.Building> building = createEnum("building", com.knu.cse.classroom.domain.Building.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.knu.cse.member.model.Major> major = createEnum("major", com.knu.cse.member.model.Major.class);

    public final BooleanPath returnCheck = createBoolean("returnCheck");

    public final NumberPath<Long> roomNumber = createNumber("roomNumber", Long.class);

    public final NumberPath<Long> seatNumber = createNumber("seatNumber", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath studentId = createString("studentId");

    public final StringPath username = createString("username");

    public QReservationSave(String variable) {
        super(ReservationSave.class, forVariable(variable));
    }

    public QReservationSave(Path<? extends ReservationSave> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationSave(PathMetadata metadata) {
        super(ReservationSave.class, metadata);
    }

}

