package me.capstone.javis.teamtodo.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamTodo is a Querydsl query type for TeamTodo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamTodo extends EntityPathBase<TeamTodo> {

    private static final long serialVersionUID = 872113482L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamTodo teamTodo = new QTeamTodo("teamTodo");

    public final me.capstone.javis.common.domain.QBaseTimeEntity _super = new me.capstone.javis.common.domain.QBaseTimeEntity(this);

    public final BooleanPath checkAlarm = createBoolean("checkAlarm");

    public final BooleanPath completed = createBoolean("completed");

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath deadLine = createString("deadLine");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final me.capstone.javis.location.data.domain.QLocation location;

    public final StringPath startLine = createString("startLine");

    public final me.capstone.javis.team.data.domain.QTeam team;

    public final StringPath title = createString("title");

    public QTeamTodo(String variable) {
        this(TeamTodo.class, forVariable(variable), INITS);
    }

    public QTeamTodo(Path<? extends TeamTodo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamTodo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamTodo(PathMetadata metadata, PathInits inits) {
        this(TeamTodo.class, metadata, inits);
    }

    public QTeamTodo(Class<? extends TeamTodo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new me.capstone.javis.location.data.domain.QLocation(forProperty("location"), inits.get("location")) : null;
        this.team = inits.isInitialized("team") ? new me.capstone.javis.team.data.domain.QTeam(forProperty("team")) : null;
    }

}

