package me.capstone.javis.todo.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TodoUpdateReqDto(
        @Schema(
                example = "다이소 에서 살 물건들",
                description = "투두 제목을 입력해주세요."
        )
        String title,
        @Schema(
                example = "1.손톱깎기, 2.아이스크림, 3. 물통",
                description = "할 일 내용을 작성해주세요."
        )
        String contents,
        @Schema(
                example = "2024-10-29",
                description = "시작 시간을 작성해주세요."
        )
        String startLine,
        @Schema(
                example = "2024-10-29",
                description = "마감 기한을 작성해주세요."
        )
        String deadLine
){}