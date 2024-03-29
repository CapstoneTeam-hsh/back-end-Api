package me.capstone.javis.user.data.dto.response.userhomePage;

import lombok.Builder;

import java.util.List;

@Builder
public record TeamAndTeamTodosResDto(
        Long teamId,
        String teamName,
        List<TeamTodoIdAndNameDto> teamTodoList
){}
