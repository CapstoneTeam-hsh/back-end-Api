package me.capstone.javis.team.data.dto.response;

import lombok.Builder;
import me.capstone.javis.team.data.domain.Team;

@Builder
public record TeamResDto(
    Long id,
    String name
){
    public static TeamResDto toDto(Team team){
        return TeamResDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }
}
