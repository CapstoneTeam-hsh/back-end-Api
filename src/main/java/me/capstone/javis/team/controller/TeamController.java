package me.capstone.javis.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.team.data.dto.request.TeamReqDto;
import me.capstone.javis.team.data.dto.response.TeamResDto;
import me.capstone.javis.team.data.dto.response.UserInfoResDto;
import me.capstone.javis.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[Team] Team API", description = "그룹 생성,유저의 그룹들 조회,그룹에 유저 추가")
@Slf4j
@RequestMapping("/teams")
@RestController
@RequiredArgsConstructor
@Validated
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "유저의 그룹들 조회", description = "유저의 그룹들을 조회합니다.")
    @Parameter(name = "loginId", description = "그룹들을 조회 할 유저의 로그인 아이디")
    @GetMapping()
    public ResponseEntity<CommonResponseDto<List<TeamResDto>>> getAllTeam(@RequestParam(name = "loginId") String loginId) {
        log.info("[getAllTeam] 로그인 아이디로 유저 정보를 가져옵니다. loginId : {}", loginId);
        List<TeamResDto> teamResDtoList = teamService.getAllTeamOfUser(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "유저의 그룹들을 성공적으로 조회하였습니다.",
                        teamResDtoList));
    }

    @Operation(summary = "그룹 생성", description = "그룹을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<CommonResponseDto<TeamResDto>> createTeam(@AuthenticationPrincipal UserDetails principal,
                                                                    @Validated @RequestBody TeamReqDto teamReqDto){

        String loginId = principal.getUsername();
        log.info("[createUser] 그룹을 생성합니다. loginId : {}", loginId);
        TeamResDto teamResDto = teamService.createTeam(loginId,teamReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹 성성이 성공적으로 완료되었습니다.",
                        teamResDto));
    }

    @Operation(summary = "그룹에 유저 추가",description = "그룹에 유저를 추가합니다.")
    @Parameters({
            @Parameter(name = "teamId", description = "유저를 추가 시킬 팀 id"),
            @Parameter(name = "loginId", description = "추가 시킬 유저의 loginId")
    })
    @PostMapping("/addition")
    public ResponseEntity<CommonResponseDto<List<UserInfoResDto>>> addUserToTeam(@RequestParam(name = "teamId")Long teamId,
                                                                                 @RequestParam(name = "loginId")String loginId){
        log.info("[addUserToTeam] 그룹에 유저를 추가합니다. teamId : {}, loginId: {}",teamId,loginId);
        List<UserInfoResDto> userInfoResDtoList = teamService.addUserToTeam(teamId,loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹에 유저를 성공적으로 추가하였습니다.",
                        userInfoResDtoList));
    }

    @Operation(summary = "그룹 참여자 목록", description = "그룹에 속해있는 유저들의 이름을 조회합니다.")
    @Parameter(name="teamId", description = "참여자를 확인 할 그룹 id")
    @GetMapping("/users/{teamId}")
    public ResponseEntity<CommonResponseDto<List<String>>> getTeamOfUsers(@PathVariable("teamId")Long teamId){
        log.info("[getTeamOfUsers] 그룹에 속해있는 사용자 이름 조회");
        List<String> userNameList = teamService.getUsersOfTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹에 해당하는 유저들 조회를 성공적으로 완료하였습니다.",
                        userNameList));
    }


    @Operation(summary = "그룹 탈퇴", description = "그룹에서 탈퇴합니다.")
    @Parameter(name="teamId", description = "탈퇴 할 그룹의 id")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<CommonResponseDto<Void>> exitTeam(@AuthenticationPrincipal UserDetails principal,
                                                            @PathVariable("teamId") Long teamId) {
        log.info("[exitTeam] 그룹에서 탈퇴합니다. teamId {}", teamId);
        String loginId = principal.getUsername();
        teamService.exitTeam(teamId, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹 탈퇴를 성공적으로 완료하였습니다.",
                        null));
    }
}
