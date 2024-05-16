package me.capstone.javis.user.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpReqDto(

       @Schema(
                example = "홍길순",
                description = "유저의 이름을 입력해주세요"
        )
        String name,

        @Size(min = 4, max = 10)
        @Pattern(regexp = "[a-z0-9]*$", message = "영어 또는 숫자로 입력해야합니다.")
        @Schema(
                example = "kang4746",
                description = "유저의 로그인 아이디를 입력해주세요"
        )
        String loginId,

        @Size(min = 8, max=20)
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$", message = "비밀번호는 영어, 숫자 조합으로 입력해야 합니다." )
        @Schema(
                example = "kang4746",
                description = "유저의 비밀번호를 입력해주세요"
        )
        String password,

        @NotNull
        @Email(message = "이메일 형식을 맞춰주세요")
        @Schema(
                example = "strong@gmail.com",
                description = "유저의 이메일을 입력해주세요"
        )
        String email
){}
