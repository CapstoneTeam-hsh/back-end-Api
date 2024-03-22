package me.capstone.javis.user.data.dto.response.calendar;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryAndAllTodoResDto(
        Long categoryId,
        String categoryName,
        List<AllTodoResDto> allTodoList
){}
