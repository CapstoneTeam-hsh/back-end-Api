package me.capstone.javis.teamtodo.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.teamtodo.data.domain.TeamTodo;

@Builder
public record TeamTodoReqDto(
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
        String deadLine,
        @Schema(
                example = "그룹 id",
                description = "투두를 작성할 그룹 id를 입력해주세요"
        )
        Long teamId,
        @Schema(
                example = "좌표 id",
                description = "좌표 id 를 입력해주세요"
        )
        Long locationId
){
        public static TeamTodo toEntity(TeamTodoReqDto todoReqDto, Team team, Location location){
                return TeamTodo.builder()
                        .title(todoReqDto.title())
                        .contents(todoReqDto.contents())
                        .startLine(todoReqDto.startLine())
                        .deadLine(todoReqDto.deadLine())
                        .team(team)
                        .location(location)
                        .build();
        }
}
