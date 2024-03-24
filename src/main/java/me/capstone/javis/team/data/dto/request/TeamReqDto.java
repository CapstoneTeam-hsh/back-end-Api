package me.capstone.javis.team.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.todo.data.domain.Todo;

@Builder
public record TeamReqDto(
        @Schema(
                example = "가족",
                description = "그룹 생성을 위해 그룹명을 작성해주세요."
        )
        String name
){
        public static Team toEntity(TeamReqDto teamReqDto){
                return Team.builder()
                        .name(teamReqDto.name())
                        .build();

        }
}
