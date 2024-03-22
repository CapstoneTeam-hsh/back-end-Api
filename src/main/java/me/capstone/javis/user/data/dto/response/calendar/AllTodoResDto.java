package me.capstone.javis.user.data.dto.response.calendar;

import lombok.Builder;

@Builder
public record AllTodoResDto (
        Long todoId,
        String title,
        String contents,
        Boolean completed,
        String startLine,
        String deadLine
){}
