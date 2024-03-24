package me.capstone.javis.teamtodo.data.dto.response;

import lombok.Builder;
import me.capstone.javis.teamtodo.data.domain.TeamTodo;

@Builder
public record TeamTodoResDto(
        Long id,
        String title,
        String contents,
        Boolean completed,
        String startLine,
        String deadLine,
        Long teamId,
        Long locationId
){
    public static TeamTodoResDto toDto(TeamTodo teamTodo){
        return TeamTodoResDto.builder()
                .id(teamTodo.getId())
                .title(teamTodo.getTitle())
                .contents(teamTodo.getContents())
                .completed(teamTodo.getCompleted())
                .startLine(teamTodo.getStartLine())
                .deadLine(teamTodo.getDeadLine())
                .teamId(teamTodo.getTeam().getId())
                .locationId(teamTodo.getLocation().getId())
                .build();
    }
}
