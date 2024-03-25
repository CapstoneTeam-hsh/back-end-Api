package me.capstone.javis.user.data.dto.response.userhomePage;

import lombok.Builder;

@Builder
public record TodoIdAndNameResDto(
        Long todoId,
        String title,
        String startLine,
        String deadLine
){}
