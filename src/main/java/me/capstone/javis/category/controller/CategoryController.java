package me.capstone.javis.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.category.data.dto.response.CategoryResDto;
import me.capstone.javis.category.service.CategoryService;
import me.capstone.javis.common.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[Category] Category API", description = "카테고리 생성, 카테고리 조회, 카테고리 삭제")
@RestController
@Slf4j
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "투두에 해당하는 카테고리 추가",description = "해당 투두의 카테고리를 셍성합니다.<br>카테고리 추가 화면")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<CategoryResDto>> createTodo(@RequestParam String name){
        log.info("[createTodo] 해당 투두의 카테고리를 생성합니다. categoryName : {}", name);

        String loginId = getLoginId();
        CategoryResDto categoryResDto = categoryService.makeCategory(loginId, name);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 생성이 성공적으로 완료되었습니다.",
                        categoryResDto));

    }

    @Operation(summary = "유저의 카테고리 리스트 조회", description = "유저의 카테고리 리스트를 전부 불러옵니다.<br>투두리스트 추가 화면, 메뉴 화면")
    @GetMapping()
    public ResponseEntity<CommonResponseDto<List<String>>> getCategoryList(){

        String loginId = getLoginId();
        List<String> categoryNames = categoryService.getCategoryNames(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 리스트 조회를 성공적으로 완료하였습니다.",
                        categoryNames
                ));
    }

    public String getLoginId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}

