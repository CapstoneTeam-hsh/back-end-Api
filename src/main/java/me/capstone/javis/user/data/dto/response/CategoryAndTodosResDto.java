package me.capstone.javis.user.data.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryAndTodosResDto(
        Long categoryId,
        String categoryName,
        List<String> todoTitleList
){}
