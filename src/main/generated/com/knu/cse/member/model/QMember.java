package com.knu.cse.member.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1159027439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.knu.cse.base.QBaseEntity _super = new com.knu.cse.base.QBaseEntity(this);

    public final ListPath<com.knu.cse.board.domain.Board, com.knu.cse.board.domain.QBoard> boardList = this.<com.knu.cse.board.domain.Board, com.knu.cse.board.domain.QBoard>createList("boardList", com.knu.cse.board.domain.Board.class, com.knu.cse.board.domain.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.knu.cse.comment.domain.Comment, com.knu.cse.comment.domain.QComment> commentList = this.<com.knu.cse.comment.domain.Comment, com.knu.cse.comment.domain.QComment>createList("commentList", com.knu.cse.comment.domain.Comment.class, com.knu.cse.comment.domain.QComment.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<Major> major = createEnum("major", Major.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final com.knu.cse.reservation.domain.QReservation reservation;

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public final StringPath studentId = createString("studentId");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new com.knu.cse.reservation.domain.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

