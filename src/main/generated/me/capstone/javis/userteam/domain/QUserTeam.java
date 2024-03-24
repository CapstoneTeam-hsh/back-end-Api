package me.capstone.javis.userteam.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserTeam is a Querydsl query type for UserTeam
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserTeam extends EntityPathBase<UserTeam> {

    private static final long serialVersionUID = 792193910L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserTeam userTeam = new QUserTeam("userTeam");

    public final me.capstone.javis.common.domain.QBaseTimeEntity _super = new me.capstone.javis.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final me.capstone.javis.team.data.domain.QTeam team;

    public final me.capstone.javis.user.data.domain.QUser user;

    public QUserTeam(String variable) {
        this(UserTeam.class, forVariable(variable), INITS);
    }

    public QUserTeam(Path<? extends UserTeam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserTeam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserTeam(PathMetadata metadata, PathInits inits) {
        this(UserTeam.class, metadata, inits);
    }

    public QUserTeam(Class<? extends UserTeam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new me.capstone.javis.team.data.domain.QTeam(forProperty("team")) : null;
        this.user = inits.isInitialized("user") ? new me.capstone.javis.user.data.domain.QUser(forProperty("user")) : null;
    }

}

