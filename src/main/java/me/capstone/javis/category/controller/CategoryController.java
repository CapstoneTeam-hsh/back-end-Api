package me.capstone.javis.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.category.data.dto.response.CategoryResDto;
import me.capstone.javis.category.service.CategoryService;
import me.capstone.javis.common.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "카테고리 단건 조회", description = "카테고리 id로 카테고리 하나 단건 조회")
    @Parameter(name = "categoryId", description = "조회 할 카테고리 id")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CommonResponseDto<CategoryResDto>> getCategory(@PathVariable("categoryId") Long categoryId){
        log.info("[getCategory] id에 해당하는 카테고리를 조회합니다.");
        CategoryResDto categoryResDto = categoryService.getOneCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 단건 조회를 성공적으로 완료되었습니다.",
                        categoryResDto));

    }

    @Operation(summary = "투두에 해당하는 카테고리 추가",description = "해당 투두의 카테고리를 셍성합니다.<br>카테고리 추가 화면")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<CategoryResDto>> createTodo(@AuthenticationPrincipal UserDetails principal,
                                                                        @RequestParam(required = false) String name){
        log.info("[createTodo] 해당 투두의 카테고리를 생성합니다. categoryName : {}", name);
        String loginId = principal.getUsername();
        CategoryResDto categoryResDto = categoryService.makeCategory(loginId, name);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 생성이 성공적으로 완료되었습니다.",
                        categoryResDto));

    }

    @Operation(summary = "유저의 카테고리 리스트 조회", description = "유저의 카테고리 리스트를 전부 불러옵니다.<br>투두리스트 추가 화면, 메뉴 화면")
    @GetMapping()
    public ResponseEntity<CommonResponseDto<List<String>>> getCategoryList(@AuthenticationPrincipal UserDetails principal){
        String loginId = principal.getUsername();
        List<String> categoryNames = categoryService.getCategoryNames(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 리스트 조회를 성공적으로 완료하였습니다.",
                        categoryNames
                ));
    }

    @Operation(summary = "유저의 카테고리 삭제", description = "유저의 카테고리를 삭제합니다.")
    @Parameter(name = "categoryName", description = "카테고리 이름으로 유저의 카테고리를 삭제합니다.")
    @DeleteMapping()
    public ResponseEntity<CommonResponseDto<Void>> deleteCategory(@RequestParam("categoryName") String categoryName){
        categoryService.deleteCategory(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 삭제를 성공적으로 완료하였습니다.",
                        null));

    }

    @Operation(summary = "카테고리 이름 수정", description = "카테고리 이름을 수정합니다.")
    @Parameters({
            @Parameter(name = "categoryId", description = "수정할 카테고리 id"),
            @Parameter(name = "categoryName", description = "수정할 카테고리 name")
    })
    @PutMapping()
    public ResponseEntity<CommonResponseDto<CategoryResDto>> updateCategoryName(@RequestParam("categoryId")Long categoryId,
                                                                                @RequestParam("categoryName")String categoryName){
        CategoryResDto categoryResDto = categoryService.updateCategoryName(categoryId,categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "카테고리 이름 수정을 성공적으로 완료하였습니다.",
                        categoryResDto));
    }
}

