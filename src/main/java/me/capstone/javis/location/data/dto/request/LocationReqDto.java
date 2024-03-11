package me.capstone.javis.location.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LocationReqDto(
        @Schema(
                example = "해당 좌표 이름",
                description = "좌표 위치 이름을 입력해주세요."
        )
        String name,
        @Schema(
                example = "좌표의 위도",
                description = "좌표의 위도를 입력해주세요."
        )
        double latitude,
        @Schema(
                example = "좌표의 경도",
                description = "좌표의 경도를 입력해주세요."
        )
        double longitude
){}
