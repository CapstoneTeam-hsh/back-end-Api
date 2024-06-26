package me.capstone.javis.todo.data.dto.response;

import lombok.Builder;

@Builder
public record TodoSimpleInfoResDto(
        Long id,
        String title,
        Boolean checkAlarm,
        double latitude,
        double longitude
){}
