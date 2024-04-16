package me.capstone.javis.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.user.data.dto.request.SignInReqDto;
import me.capstone.javis.user.data.dto.request.SignUpReqDto;
import me.capstone.javis.user.data.dto.request.UserUpdateDto;
import me.capstone.javis.user.data.dto.response.SignInResDto;
import me.capstone.javis.user.data.dto.response.SignUpResDto;
import me.capstone.javis.user.data.dto.response.UserResDto;
import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TeamAndTeamTodosResDto;
import me.capstone.javis.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[User] User API", description = "유저 생성,유저 조회, 유저 수정, 유저 삭제")
@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회",description = "유저의 정보를 조회합니다.<br>유저 정보 페이지")
    @GetMapping()
    public ResponseEntity<CommonResponseDto<SignUpResDto>> getUser(@AuthenticationPrincipal UserDetails principal){
        String loginId = principal.getUsername();
        SignUpResDto signUpResDto = userService.getHomePageInfo(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저 정보가 성공적으로 조회되었습니다.",
                        signUpResDto));
    }



    @Operation(summary = "유저 삭제",description = "유저 번호로 유저를 삭제합니다.")
    //@Parameter(name = "userId", description = "삭제할 유저의 id")
    @DeleteMapping()
    public ResponseEntity<CommonResponseDto<Void>> deleteUser(@AuthenticationPrincipal UserDetails principal){
        String loginId = principal.getUsername();
        userService.deleteUser(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저 삭제가 성공적으로 완료되었습니다.",
                        null));
    }

    @Operation(summary = "회원가입", description = "회원 가입 할 유저의 정보를 입력합니다.<br> -회원가입 화면-")
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponseDto<SignUpResDto>> signUp(@Validated @RequestBody SignUpReqDto userReqDto){
        //String role = "USER";
        SignUpResDto userResDto = userService.signUp(userReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto<>(
                        "유저 회원가입을 성공적으로 완료하였습니다.",
                        userResDto));
    }

    @Operation(summary = "로그인", description = "회원 가입 한 유저의 loginId와 password를 입력합니다.<br> -로그인 화면-")
    @PostMapping("/sign-in")
    public ResponseEntity<CommonResponseDto<SignInResDto>> signIn(@Validated @RequestBody SignInReqDto signInReqDto){
        SignInResDto signInResDto = userService.signIn(signInReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저 로그인을 성공적으로 완료하였습니다.",
                        signInResDto));
    }

    @Operation(summary = "유저의 카테고리들과 카테고리에 해당하는 투두들의 제목, 날짜 조회", description = "유저의 카테고리 리스트와 각 카테고리에 해당하는 투두 제목을 가져옵니다.<br> -메인 화면-")
    @GetMapping("/homepage/category")
    public ResponseEntity<CommonResponseDto<List<CategoryAndTodosResDto>>> getCategoryAndTodos(@AuthenticationPrincipal UserDetails principal){

        String loginId = principal.getUsername();
        List<CategoryAndTodosResDto> categoryAndTodosResDtoList = userService.getCategoryAndTodos(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리와 투두 제목들을 성공적으로 조회 완료하였습니다.",
                        categoryAndTodosResDtoList));
    }

    @Operation(summary = "카테고리와 투두 전체 내용 리턴", description = "유저의 카테고리 리스트와 각 카테고리에 해당하는 투두 전체 내영을 리턴합니다. <br> - 캘린더 - ")
    @GetMapping("/calendar")
    public ResponseEntity<CommonResponseDto<List<CategoryAndAllTodoResDto>>> getCategoryAndAllTodos(@AuthenticationPrincipal UserDetails principal){

        String loginId = principal.getUsername();
        List<CategoryAndAllTodoResDto> categoryAndAllTodosResDtoList = userService.getCategoryAndAllTodos(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리와 투두 전체 내용 들을 성공적으로 조회 완료하였습니다.",
                        categoryAndAllTodosResDtoList));
    }

    @Operation(summary = "그룹 과 그룹 투두 전체 내용 리턴", description = "유저의 그룹들과 해당 그룹의 할 일 내용을 조회합니다.")
    @GetMapping("/homepage/team")
    public ResponseEntity<CommonResponseDto<List<TeamAndTeamTodosResDto>>> getTeamAndTeamTodos(@AuthenticationPrincipal UserDetails principal){

        String loginId = principal.getUsername();
        List<TeamAndTeamTodosResDto> teamAndTeamTodosResDtoList = userService.getTeamAndAllTeamTodos(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저의 그룹들과 그룹 투두 들을 성공적으로 조회 완료하였습니다.",
                        teamAndTeamTodosResDtoList));
    }

    @Operation(summary = "유저 정보 수정", description = "유저의 정보를 수정합니다.")
    @PutMapping()
    public ResponseEntity<CommonResponseDto<UserResDto>> updateUser(@AuthenticationPrincipal UserDetails principal,
                                                                    @Validated @RequestBody UserUpdateDto userUpdateDto){
        String loginId = principal.getUsername();
        UserResDto userResDto = userService.updateUser(loginId, userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저의 정보 수정을 성공적으로 완료하였습니다.",
                        userResDto));
    }

    @Operation(summary = "유저 비밀번호 수정",description = "해당 유저의 비밀번호를 수정합니다.")
    @Parameter(name = "password", description = "수정할 비밀번호를 입력해주세요.")
    @PutMapping("/updatePassword")
    public ResponseEntity<CommonResponseDto<Void>> changePassword(@AuthenticationPrincipal UserDetails principal,
                                                                  @RequestParam("password") String password){
        String loginId = principal.getUsername(); // 로그인한 사용자의 아이디

        userService.updatePassword(loginId, password);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저 비밀번호 수정이 성공적으로 완료되었습니다.",
                        null));
    }

    @Operation(summary = "유저 비밀번호 수정 전 현재 비밀번호 체크",description = "해당 유저의 비밀번호를 체크하여 비밀번호 수정 가능 여부를 판단합니다.")
    @Parameter(name = "password", description = "체크 할 비밀번호를 입력해주세요.")
    @PutMapping("/checkPassword")
    public ResponseEntity<CommonResponseDto<Boolean>> checkPassword(@AuthenticationPrincipal UserDetails principal,
                                                                    @RequestParam("password") String password){
        String loginId = principal.getUsername(); // 로그인한 사용자의 아이디

        Boolean check = userService.checkPassword(loginId, password);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "비밀번호 체크 성공 여부 응답",
                        check));
    }
}
