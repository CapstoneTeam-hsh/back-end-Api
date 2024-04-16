package me.capstone.javis.common.exception.customs;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    // SignIn Exception
    PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST,"잘못된 비밀번호를 입력하셨습니다."),

    // User Exception
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "사용자가 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),
    PASSWORD_DUPLICATE(HttpStatus.BAD_REQUEST,"이전 비밀번호와 같은 내용으로 비밀번호를 수정 할 수 없습니다."),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 loginId 입니다."),

    // _Todo Exception
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND,"투두를 찾을 수 없습니다."),

    // Category Exception
    DUPLICATE_CATEGORY_NAME(HttpStatus.BAD_REQUEST,"중복된 카테고리 이름입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"카테고리를 찾을 수 없습니다."),

    // Location Exception
    LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"좌표를 찾을 수 없습니다."),

    // Team Exception
    TEAM_NOT_FOUND(HttpStatus.BAD_REQUEST,"그룹을 찾을 수 없습니다."),

    // UserTeam Exception
    USER_TEAM_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 그룹에 가입 된 유저가 존재하지 않습니다."),

    // TeamTodo Exception
    TEAM_TODO_NOT_FOUND(HttpStatus.BAD_REQUEST,"그룹 투두를 찾을 수 없습니다."),

    //Jwt Exception
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,  "토큰이 만료되었습니다."),
    TOKEN_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "권한이 없습니다"),
    TOKEN_UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증되지 않은 토큰입니다."),
    TOKEN_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰이 비었거나 null입니다");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
