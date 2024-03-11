package me.capstone.javis.common.dto;

public record CommonResponseDto<T>(
        String msg,
        T result
) {
}
