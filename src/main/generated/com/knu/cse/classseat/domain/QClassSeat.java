package com.knu.cse.classseat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassSeat is a Querydsl query type for ClassSeat
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClassSeat extends EntityPathBase<ClassSeat> {

    private static final long serialVersionUID = 1117969990L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassSeat classSeat = new QClassSeat("classSeat");

    public final com.knu.cse.base.QBaseEntity _super = new com.knu.cse.base.QBaseEntity(this);

    public final com.knu.cse.classroom.domain.QClassRoom classRoom;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final com.knu.cse.reservation.domain.QReservation reservation;

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QClassSeat(String variable) {
        this(ClassSeat.class, forVariable(variable), INITS);
    }

    public QClassSeat(Path<? extends ClassSeat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassSeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassSeat(PathMetadata metadata, PathInits inits) {
        this(ClassSeat.class, metadata, inits);
    }

    public QClassSeat(Class<? extends ClassSeat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classRoom = inits.isInitialized("classRoom") ? new com.knu.cse.classroom.domain.QClassRoom(forProperty("classRoom")) : null;
        this.reservation = inits.isInitialized("reservation") ? new com.knu.cse.reservation.domain.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

