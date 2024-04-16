package me.capstone.javis.location.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.batch.item.mail.MailErrorHandler;

@Builder
public record LocationReqDto(

        @NotBlank(message = "해당 좌표의 이름을 입력해주세요.")
        @Schema(
                example = "해당 좌표 이름",
                description = "좌표 위치 이름을 입력해주세요."
        )
        String name,

        @NotNull(message = "위도를 제대로 입력해주세요. 위도는 실수 자료형 입니다.")
        @Schema(
                example = "좌표의 위도",
                description = "좌표의 위도를 입력해주세요."
        )
        double latitude,

        @NotNull(message = "경도를 제대로 입력해주세요. 경도는 실수 자료형 입니다.")
        @Schema(
                example = "좌표의 경도",
                description = "좌표의 경도를 입력해주세요."
        )
        double longitude
){}
