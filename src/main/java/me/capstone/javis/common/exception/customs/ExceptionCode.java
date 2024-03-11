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
    // _Todo Exception
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND,"투두를 찾을 수 없습니다."),
    // Category Exception
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"카테고리를 찾을 수 없습니다."),
    // Location Exception
    LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"좌표를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
