package me.capstone.javis.teamtodo.data.repository;

import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;

import java.util.List;

public interface TeamTodoCustomRepository {

    List<TeamTodoResDto> findAllTeamTodoByTeam(Long reqTeamId);
    List<TodoSimpleInfoResDto> findTeamTodoListByTeam(Long reqTeamId);

}
