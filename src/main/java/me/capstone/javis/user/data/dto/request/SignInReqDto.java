package me.capstone.javis.user.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignInReqDto(


        @Size(min = 4, max = 10, message = "4 ~ 10 자 사이로 loginId 를 작성 해 주세요.")
        @Pattern(regexp = "[a-z0-9]*$", message = "loginId는 숫자와 영어로 작성 해야 합니다.")
        @Schema(
                example = "kang4746",
                description = "유저의 loginId를 입력해주세요"
        )
        String loginId,
        @Size(min = 8, max=20, message = "8 ~ 20 자 사이로 비밀번호를 작성 해 주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$", message = "비밀번호는 영어, 숫자 조합으로 입력해야 합니다." )
        @Schema(
                example = "kang4746",
                description = "유저의 password를 입력해주세요"
        )
        String password
)
{}
