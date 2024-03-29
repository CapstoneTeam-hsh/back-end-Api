package me.capstone.javis.user.data.dto.response;

import lombok.Builder;
import me.capstone.javis.user.data.domain.User;

@Builder

public record UserResDto(
        Long id,
        String name,
        String loginId,
        String email
){
    public static UserResDto toDto(User user){
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .build();
    }
}
