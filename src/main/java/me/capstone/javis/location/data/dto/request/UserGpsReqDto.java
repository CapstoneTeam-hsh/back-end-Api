package me.capstone.javis.location.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserGpsReqDto(

        @NotNull(message = "위도를 제대로 입력해주세요. 위도는 실수 자료형입니다.")
        @Schema(
                example = "현재 사용자의 위도",
                description = "현재 사용자의 위도를 입력."
        )
        double latitude,
        @NotNull(message = "경도를 제대로 입력해주세요. 경도는 실수 자료형입니다.")
        @Schema(
                example = "현재 사용자의 경도",
                description = "현재 사용자의 경도를 입력."
        )
        double longitude
){}
