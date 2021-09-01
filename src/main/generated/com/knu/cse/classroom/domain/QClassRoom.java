package com.knu.cse.classroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassRoom is a Querydsl query type for ClassRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClassRoom extends EntityPathBase<ClassRoom> {

    private static final long serialVersionUID = 565191942L;

    public static final QClassRoom classRoom = new QClassRoom("classRoom");

    public final EnumPath<Building> building = createEnum("building", Building.class);

    public final ListPath<com.knu.cse.classseat.domain.ClassSeat, com.knu.cse.classseat.domain.QClassSeat> classSeats = this.<com.knu.cse.classseat.domain.ClassSeat, com.knu.cse.classseat.domain.QClassSeat>createList("classSeats", com.knu.cse.classseat.domain.ClassSeat.class, com.knu.cse.classseat.domain.QClassSeat.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final NumberPath<Long> totalSeats = createNumber("totalSeats", Long.class);

    public QClassRoom(String variable) {
        super(ClassRoom.class, forVariable(variable));
    }

    public QClassRoom(Path<? extends ClassRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassRoom(PathMetadata metadata) {
        super(ClassRoom.class, metadata);
    }

}

