package me.capstone.javis.teamtodo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.teamtodo.data.dto.request.TeamTodoReqDto;
import me.capstone.javis.teamtodo.data.dto.response.TeamTodoResDto;
import me.capstone.javis.teamtodo.service.TeamTodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[TeamTodo] TeamTodo API", description = "팀 투두 생성, 팀 투두 조회, 투두 삭제")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/teamTodo")
public class TeamTodoController {

    private final TeamTodoService todoService;

    @Operation(summary = "그룹 투두 전체 조회", description = "그룹 id로 해당 그룹의 투두들을 조회합니다.")
    @Parameter(name="teamId", description = "그룹 id를 입력해주세요")
    @GetMapping("/{teamId}")
    public ResponseEntity<CommonResponseDto<List<TeamTodoResDto>>> getAllTeamTodo(@PathVariable("teamId") Long teamId){
        log.info("[getTodo] 그룹 id로 해당 그룹의 투두들을 조회합니다. teamId : {}", teamId);

        List<TeamTodoResDto> teamTodoResDtoList = todoService.getAllTeamTodo(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹 투두가 성공적으로 조회되었습니다.",
                        teamTodoResDtoList));
    }

    @Operation(summary = "그룹 투두 생성",description = "그룹 id로 투두를 생성합니다.")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<TeamTodoResDto>> createTeamTodo(@RequestBody TeamTodoReqDto teamTodoReqDto){
        log.info("[createTeamTodo] 그룹 id로 해당 그룹의 투두를 생성합니다.. teamId : {}",teamTodoReqDto.teamId());

        TeamTodoResDto teamTodoResDto = todoService.createTeamTodo(teamTodoReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹 투두가 성공적으로 생성되었습니다.",
                        teamTodoResDto));
    }

    @Operation(summary = "그룹 투두 삭제",description = "그룹 투두 id로 그룹 투두를 삭제합니다.")
    @DeleteMapping()
    public ResponseEntity<CommonResponseDto<Void>> deleteTeamTodo(@PathVariable("teamTodoId") Long teamTodoId){
        log.info("[deleteTeamTodo] 그룹 투두 id로 그룹 투두를 삭제합니다. teamTodoId : {}",teamTodoId);

        todoService.deleteTeamTodo(teamTodoId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "그룹 투두가 성공적으로 삭제되었습니다..",
                        null));
    }

}
