package me.capstone.javis.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.todo.data.dto.request.TodoReqDto;
import me.capstone.javis.todo.data.dto.request.TodoUpdateReqDto;
import me.capstone.javis.todo.data.dto.response.TodoResDto;
import me.capstone.javis.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name ="[Todo] Todo API", description = "투두 생성, 투두 조회, 투두 삭제")
@RestController
@Slf4j
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "투두 조회",description = "투두 id로 투두를 조회합니다.")
    @Parameter(name="todoId",description = "투두 id를 입력해주세요")
    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<TodoResDto>> getTodo(@PathVariable("todoId") Long todoId){
        log.info("[getTodo] 투두 번호로 투두를 조회합니다. todoId : {}",todoId);
        TodoResDto todoResponseDto = todoService.getTodo(todoId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "투두 정보가 성공적으로 조회되었습니다.",
                        todoResponseDto));
    }

    @Operation(summary = "투두리스트 추가",description = "해당 유저의 투두를 셍성합니다.<br>투두 추가 화면")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<TodoResDto>> createTodo(@AuthenticationPrincipal UserDetails principal,
                                                                    @Validated @RequestBody TodoReqDto todoReqDto){
        log.info("[createTodo]  할일을 생성합니다.");

        String loginId = principal.getUsername();
        TodoResDto todoResponseDto = todoService.saveTodo(loginId, todoReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "투두 생성이 성공적으로 완료되었습니다.",
                        todoResponseDto));
    }

    @Operation(summary = "투두리스트 수정", description = "투두리스트 내용을 수정합니다.")
    @Parameter(name = "todoId",description = "수정 할 투두의 id")
    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<TodoResDto>> updateTodo(@PathVariable("todoId") Long todoId,
                                                                    @RequestBody TodoUpdateReqDto todoUpdateReqDto){
        log.info("[updateTodo] 투두를 수정합니다.");
        TodoResDto todoResDto = todoService.updateTodo(todoId,todoUpdateReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "투두 수정이 성공적으로 완료되었습니다.",
                        todoResDto));
    }

    @Operation(summary = "투두 삭제",description = "투두 번호로 투두를 삭제합니다.")
    @Parameter(name="todoId",description = "투두 번호")
    @DeleteMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteTodo(@PathVariable(name="todoId") Long todoId){
        log.info("[deleteTodo] 투두를 삭제합니다. todoId: {}",todoId);
        todoService.deleteTodo(todoId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "투두 삭제가 성공적으로 완료되었습니다.",
                        null));
    }
}
