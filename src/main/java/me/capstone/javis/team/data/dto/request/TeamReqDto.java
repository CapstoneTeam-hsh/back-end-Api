package me.capstone.javis.team.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import me.capstone.javis.team.data.domain.Team;

@Builder
public record TeamReqDto(

        @Size(min = 1, max = 8,message = "1 ~ 8 자 사이로 그룹명을 입력해야합니다.")
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
