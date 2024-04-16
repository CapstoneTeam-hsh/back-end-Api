package me.capstone.javis.todo.data.dto.response;

import lombok.Builder;

@Builder
public record TodoSseResDto(
        Long userId,

        Long todoId,
        String title,
        String deadLine
){
}
