package com.knu.cse.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1377704518L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.knu.cse.base.QBaseTimeEntity _super = new com.knu.cse.base.QBaseTimeEntity(this);

    public final StringPath author = createString("author");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final ListPath<com.knu.cse.comment.domain.Comment, com.knu.cse.comment.domain.QComment> commentList = this.<com.knu.cse.comment.domain.Comment, com.knu.cse.comment.domain.QComment>createList("commentList", com.knu.cse.comment.domain.Comment.class, com.knu.cse.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.knu.cse.image.domain.Image, com.knu.cse.image.domain.QImage> imageList = this.<com.knu.cse.image.domain.Image, com.knu.cse.image.domain.QImage>createList("imageList", com.knu.cse.image.domain.Image.class, com.knu.cse.image.domain.QImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.knu.cse.member.model.QMember member;

    public final StringPath title = createString("title");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.knu.cse.member.model.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

