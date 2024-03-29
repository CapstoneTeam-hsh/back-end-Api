package me.capstone.javis.user.data.dto.response.userhomePage;

import lombok.Builder;

@Builder
public record TeamTodoIdAndNameDto(
        Long teamTodoId,
        String teamTodoTitle,
        String startLine,
        String deadLine
){}
