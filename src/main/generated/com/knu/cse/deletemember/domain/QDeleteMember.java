package com.knu.cse.deletemember.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeleteMember is a Querydsl query type for DeleteMember
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDeleteMember extends EntityPathBase<DeleteMember> {

    private static final long serialVersionUID = -1908718232L;

    public static final QDeleteMember deleteMember = new QDeleteMember("deleteMember");

    public final StringPath email = createString("email");

    public final EnumPath<com.knu.cse.member.model.Gender> gender = createEnum("gender", com.knu.cse.member.model.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.knu.cse.member.model.Major> major = createEnum("major", com.knu.cse.member.model.Major.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final EnumPath<com.knu.cse.member.model.MemberRole> role = createEnum("role", com.knu.cse.member.model.MemberRole.class);

    public final StringPath studentId = createString("studentId");

    public final StringPath username = createString("username");

    public QDeleteMember(String variable) {
        super(DeleteMember.class, forVariable(variable));
    }

    public QDeleteMember(Path<? extends DeleteMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeleteMember(PathMetadata metadata) {
        super(DeleteMember.class, metadata);
    }

}

