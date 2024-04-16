package me.capstone.javis.teamtodo.service;

import lombok.RequiredArgsConstructor;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.location.data.repository.LocationRepository;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.team.data.repository.TeamRepository;
import me.capstone.javis.teamtodo.data.domain.TeamTodo;
import me.capstone.javis.teamtodo.data.dto.request.TeamTodoReqDto;
import me.capstone.javis.teamtodo.data.dto.request.TeamTodoUpdateDto;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;
import me.capstone.javis.teamtodo.data.repository.TeamTodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamTodoService {

    private final TeamTodoRepository teamTodoRepository;
    private final TeamRepository teamRepository;
    private final LocationRepository locationRepository;



    public TeamTodoResDto getOneTeamTodo(Long teamTodoId){
        TeamTodo teamTodo = teamTodoRepository.findById(teamTodoId).orElseThrow(()->new CustomException(ExceptionCode.TEAMTODO_NOT_FOUND));

        return TeamTodoResDto.toDto(teamTodo);
    }


    public TeamTodoResDto createTeamTodo(TeamTodoReqDto todoReqDto){
        Team team = teamRepository.findById(todoReqDto.teamId()).orElseThrow(()->new CustomException(ExceptionCode.TEAM_NOT_FOUND));
        Location location = locationRepository.findById(todoReqDto.locationId()).orElseThrow(() -> new CustomException(ExceptionCode.LOCATION_NOT_FOUND));

        TeamTodo teamTodo = TeamTodoReqDto.toEntity(todoReqDto,team,location);

        return TeamTodoResDto.toDto(teamTodoRepository.save(teamTodo));
    }

    public TeamTodoResDto updateTeamTodo(Long teamTodoId, TeamTodoUpdateDto teamTodoUpdateDto){
        TeamTodo updateTeamTodo = teamTodoRepository.findById(teamTodoId).orElseThrow(()-> new CustomException(ExceptionCode.TEAMTODO_NOT_FOUND));

        if (teamTodoUpdateDto.title() != null && !teamTodoUpdateDto.title().isEmpty())
        {
            updateTeamTodo.updateTitle(teamTodoUpdateDto.title());
        }
        if (teamTodoUpdateDto.contents() != null && !teamTodoUpdateDto.contents().isEmpty())
        {
            updateTeamTodo.updateContent(teamTodoUpdateDto.contents());
        }
        if (teamTodoUpdateDto.startLine() != null && !teamTodoUpdateDto.startLine().isEmpty())
        {
            updateTeamTodo.updateStartLine(teamTodoUpdateDto.startLine());
        }
        if (teamTodoUpdateDto.deadLine() != null && !teamTodoUpdateDto.deadLine().isEmpty())
        {
            updateTeamTodo.updateDeadLine(teamTodoUpdateDto.deadLine());
        }

        return  TeamTodoResDto.toDto(updateTeamTodo);
    }

    @Transactional(readOnly = true)
    public List<TeamTodoResDto> getAllTeamTodo(Long teamId){

        List<TeamTodoResDto> teamTodoResDtoList = teamTodoRepository.findAllTeamTodoByTeam(teamId);

        return teamTodoResDtoList;
    }

    public void deleteTeamTodo(Long teamTodoId){
        teamTodoRepository.deleteById(teamTodoId);
    }
}
