package me.capstone.javis.user.data.dto.response.userhomePage;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryAndTodosResDto(
        Long categoryId,
        String categoryName,
        List<TodoIdAndNameResDto> todoList
){}
