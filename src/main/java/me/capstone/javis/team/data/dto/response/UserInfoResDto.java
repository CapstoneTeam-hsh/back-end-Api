package me.capstone.javis.team.data.dto.response;

import lombok.Builder;
import me.capstone.javis.user.data.domain.User;

@Builder
public record UserInfoResDto(
        Long id,
        String name,
        String loginId,
        String password,
        String email
){
    public static UserInfoResDto toDto(User user){
        return UserInfoResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
