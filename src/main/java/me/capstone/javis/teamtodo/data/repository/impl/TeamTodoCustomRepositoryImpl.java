package me.capstone.javis.teamtodo.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;
import me.capstone.javis.teamtodo.data.repository.TeamTodoCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static me.capstone.javis.team.data.domain.QTeam.team;
import static me.capstone.javis.teamtodo.data.domain.QTeamTodo.teamTodo;

@Repository
@RequiredArgsConstructor
public class TeamTodoCustomRepositoryImpl implements TeamTodoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<TeamTodoResDto> findAllTeamTodoByTeam(Long reqTeamId) {

        List<Tuple> allTeamTodo = jpaQueryFactory
                .select(teamTodo.id,teamTodo.title,teamTodo.contents,teamTodo.completed,teamTodo.startLine, teamTodo.deadLine,teamTodo.team.id, teamTodo.location.id)
                .from(team)
                .join(teamTodo).on(team.id.eq(teamTodo.team.id))
                .where(team.id.eq(reqTeamId))
                .fetch();

        List<TeamTodoResDto> allTeamTodoResDtoList = new ArrayList<>();

        for (Tuple teamTodoTuple : allTeamTodo){
            Long teamTodoId = teamTodoTuple.get(teamTodo.id);
            String title = teamTodoTuple.get(teamTodo.title);
            String contents = teamTodoTuple.get(teamTodo.contents);
            String startLine = teamTodoTuple.get(teamTodo.startLine);
            String deadLine = teamTodoTuple.get(teamTodo.deadLine);
            Long teamId = teamTodoTuple.get(teamTodo.team.id);
            Long locationId = teamTodoTuple.get(teamTodo.location.id);
            allTeamTodoResDtoList.add(TeamTodoResDto.builder()
                            .id(teamTodoId)
                            .title(title)
                            .contents(contents)
                            .startLine(startLine)
                            .deadLine(deadLine)
                            .teamId(teamId)
                            .locationId(locationId)
                    .build());
        }

        return allTeamTodoResDtoList;
    }
}
