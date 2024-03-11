package me.capstone.javis.todo.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.todo.data.domain.Todo;
import me.capstone.javis.user.data.domain.User;

public record TodoReqDto(
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
                description = "마감 기한을 작성해주세요."
        )
        String deadLine,
        @Schema(
                example = "친구",
                description = "카테고리 이름을 작성해주세요"
        )
        String category,
        @Schema(
                example = "좌표 id",
                description = "좌표 id 를 입력해주세요"
        )
        Long locationId
) {
    public static Todo toEntity(TodoReqDto todoRequestDto, User user, Category category, Location location){
        return Todo.builder()
                .title(todoRequestDto.title())
                .contents(todoRequestDto.contents())
                .deadLine(todoRequestDto.deadLine())
                .user(user)
                .category(category)
                .location(location)
                .build();
    }
}
