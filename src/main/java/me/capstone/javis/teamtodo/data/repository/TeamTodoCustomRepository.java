package me.capstone.javis.teamtodo.data.repository;

import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;

import java.util.List;

public interface TeamTodoCustomRepository {

    List<TeamTodoResDto> findAllTeamTodoByTeam(Long reqTeamId);

}
