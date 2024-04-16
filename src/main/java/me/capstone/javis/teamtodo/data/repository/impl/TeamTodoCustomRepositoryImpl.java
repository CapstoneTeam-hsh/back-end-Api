package me.capstone.javis.teamtodo.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;
import me.capstone.javis.teamtodo.data.repository.TeamTodoCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.capstone.javis.team.data.domain.QTeam.team;
import static me.capstone.javis.teamtodo.data.domain.QTeamTodo.teamTodo;

@Repository
@RequiredArgsConstructor
public class TeamTodoCustomRepositoryImpl implements TeamTodoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    // /teamTodo/team/{teamId} 그룹 투두 전체 조회
    @Override
    public List<TeamTodoResDto> findAllTeamTodoByTeam(Long reqTeamId) {

        List<Tuple> allTeamTodo = jpaQueryFactory
                .select(teamTodo.id,teamTodo.title,teamTodo.contents,teamTodo.completed,teamTodo.startLine, teamTodo.deadLine,teamTodo.team.id, teamTodo.location.id)
                .from(team)
                .join(teamTodo).on(team.id.eq(teamTodo.team.id))
                .where(team.id.eq(reqTeamId))
                .fetch();

        //stream 사용으로 수정
        List<TeamTodoResDto> allTeamTodoResDtoList = allTeamTodo.stream().map(
                tuple -> TeamTodoResDto.builder()
                        .id(tuple.get(teamTodo.id))
                        .title(tuple.get(teamTodo.title))
                        .contents(tuple.get(teamTodo.contents))
                        .startLine(tuple.get(teamTodo.startLine))
                        .deadLine(tuple.get(teamTodo.deadLine))
                        .teamId(tuple.get(teamTodo.team.id))
                        .locationId(tuple.get(teamTodo.location.id))
                        .build()
        ).toList();

        return allTeamTodoResDtoList;
    }
}
