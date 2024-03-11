package me.capstone.javis.location.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserGpsReqDto(
        @Schema(
                example = "현재 사용자의 위도",
                description = "현재 사용자의 위도를 입력."
        )
        double latitude,
        @Schema(
                example = "현재 사용자의 경도",
                description = "현재 사용자의 경도를 입력."
        )
        double longitude
){}
