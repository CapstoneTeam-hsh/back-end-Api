package me.capstone.javis.location.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.location.data.dto.request.LocationReqDto;
import me.capstone.javis.location.data.dto.request.UserGpsReqDto;
import me.capstone.javis.location.data.dto.response.LocationResDto;
import me.capstone.javis.location.service.LocationService;
import me.capstone.javis.todo.data.dto.response.TodoResDto;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[Location] Location API", description = "좌표 생성, 좌표 조회")
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "좌표 생성", description = "좌표를 생성합니다.")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<LocationResDto>> createLocation(@RequestBody LocationReqDto locationReqDto)
    {
        log.info("[createLocation] 해당 투두에 좌표를 입력합니다.");
        LocationResDto locationResDto = locationService.settingLocation(locationReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto<>(
                        "투두 좌표 정보가 성공적으로 저장되었습니다.",
                        locationResDto));
    }

    @Operation(summary = "좌표 계산 테스트", description = "좌표값을 입력하여 좌표 계산을 테스트 합니다.")
    @Parameters({
            @Parameter(name = "latitude", description = "위도"),
            @Parameter(name = "longitude", description = "경도")
    })
    @GetMapping()
    public ResponseEntity<CommonResponseDto<List<TodoSimpleInfoResDto>>> calculateLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude)
    {
        log.info("[calculateLocation] 현재 사용자의 좌표를 입력받아, 해당하는 할일을 찾습니다.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loginId = userDetails.getUsername();

        List<TodoSimpleInfoResDto> todoSimpleInfoResDtoList = locationService.calculateLocation(loginId,latitude,longitude);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "현재 좌표에 해당하는 할 일들을 성공적으로 조회하였습니다.",
                        todoSimpleInfoResDtoList));
    }

    @Operation(summary = "User-Todo-Get메서드 테스트", description = "유저의 Todo들을 잘 조회하는지 테스트")
    @GetMapping("/Test")
    public ResponseEntity<CommonResponseDto<List<TodoSimpleInfoResDto>>> getUserTodo()
    {
        log.info("[getUserTodo] 유저 할일을 조회하는 메서드를 테스트 하기  위함");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loginId = userDetails.getUsername();

        List<TodoSimpleInfoResDto> todoSimpleInfoResDtoList = locationService.getUserTodo(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "현재 좌표에 해당하는 할 일들을 성공적으로 조회하였습니다.",
                        todoSimpleInfoResDtoList));
    }

}
