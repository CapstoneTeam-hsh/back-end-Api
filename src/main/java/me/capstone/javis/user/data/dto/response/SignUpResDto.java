package me.capstone.javis.user.data.dto.response;


import lombok.Builder;
import me.capstone.javis.user.data.domain.User;

@Builder
public record SignUpResDto(
    Long id,
    String name,
    String loginId,
    String password,
    String email

){
    public static SignUpResDto toDto(User user){
        return SignUpResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

}
