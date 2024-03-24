package me.capstone.javis.team.data.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = 264086250L;

    public static final QTeam team = new QTeam("team");

    public final me.capstone.javis.common.domain.QBaseTimeEntity _super = new me.capstone.javis.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<me.capstone.javis.teamtodo.data.domain.TeamTodo, me.capstone.javis.teamtodo.data.domain.QTeamTodo> teamTodoList = this.<me.capstone.javis.teamtodo.data.domain.TeamTodo, me.capstone.javis.teamtodo.data.domain.QTeamTodo>createList("teamTodoList", me.capstone.javis.teamtodo.data.domain.TeamTodo.class, me.capstone.javis.teamtodo.data.domain.QTeamTodo.class, PathInits.DIRECT2);

    public final ListPath<me.capstone.javis.userteam.domain.UserTeam, me.capstone.javis.userteam.domain.QUserTeam> userToTeamList = this.<me.capstone.javis.userteam.domain.UserTeam, me.capstone.javis.userteam.domain.QUserTeam>createList("userToTeamList", me.capstone.javis.userteam.domain.UserTeam.class, me.capstone.javis.userteam.domain.QUserTeam.class, PathInits.DIRECT2);

    public QTeam(String variable) {
        super(Team.class, forVariable(variable));
    }

    public QTeam(Path<? extends Team> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeam(PathMetadata metadata) {
        super(Team.class, metadata);
    }

}

