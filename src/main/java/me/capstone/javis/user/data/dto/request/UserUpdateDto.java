package me.capstone.javis.user.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record UserUpdateDto(

        @Schema(
                example = "홍길순",
                description = "유저의 이름을 입력해주세요"
        )
        String name,

        @Size(min = 4, max = 10, message = "4 ~ 10 자 사이로 loginId 를 작성 해 주세요.")
        @Pattern(regexp = "[a-z0-9]*$")
        @Schema(
                example = "kang4746",
                description = "유저의 로그인 아이디를 입력해주세요"
        )
        String loginId,

        @NotNull
        @Email(message = "이메일 형식을 맞춰주세요")
        @Schema(
                example = "strong@gmail.com",
                description = "유저의 이메일을 입력해주세요"
        )
        @NotBlank(message = "유저 이메일 입력은 필수입니다.")
        String email
){}
