package me.capstone.javis.teamtodo.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TeamTodoUpdateDto(
        @Size(min = 1, max = 20, message = "1 ~ 20자 내로 투두 제목을 작성 해 주세요.")
        @Schema(
                example = "다이소 에서 살 물건들",
                description = "투두 제목을 입력해주세요."
        )
        String title,

        @Size(min = 1, max = 100, message = "1 ~ 100자 내로 투두 내용을 작성 해 주세요.")
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
