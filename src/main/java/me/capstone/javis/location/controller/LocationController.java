package me.capstone.javis.location.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.location.data.dto.request.LocationReqDto;
import me.capstone.javis.location.data.dto.response.LocationResDto;
import me.capstone.javis.location.service.LocationService;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="[Location] Location API", description = "좌표 생성, 좌표 조회")
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
@Validated
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "좌표 단건 조회", description = "좌표 id로 좌표를 단건 조회합니다.")
    @GetMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationResDto>> getOneLocation(@PathVariable("locationId") Long locationId){
        log.info("[getOneLocation] 좌표 id로 좌표를 단건조회합니다.");
        LocationResDto locationResDto = locationService.getOneLocation(locationId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "좌표 조회를 성공적으로 완료하였습니다.",
                        locationResDto));
    }

    @Operation(summary = "좌표 생성", description = "좌표를 생성합니다.")
    @PostMapping()
    public ResponseEntity<CommonResponseDto<LocationResDto>> createLocation(@Validated @RequestBody LocationReqDto locationReqDto)
    {
        log.info("[createLocation] 해당 투두에 좌표를 입력합니다.");
        LocationResDto locationResDto = locationService.settingLocation(locationReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto<>(
                        "투두 좌표 정보가 성공적으로 저장되었습니다.",
                        locationResDto));
    }

    @Operation(summary = "좌표 삭제", description = "좌표를 삭제합니다.")
    @Parameter(name = "locationId", description = "좌표 id로 좌표를 삭제합니다.")
    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteLocation(@PathVariable("locationId") Long locationId){
        log.info("[deleteLocation] 좌표를 삭제합니다.");

        locationService.deleteLocation(locationId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "좌표 삭제를 성공적으로 완료하였습니다.",
                        null));
    }

    @Operation(summary = "좌표 계산 테스트", description = "좌표값을 입력하여 좌표 계산을 테스트 합니다.")
    @Parameters({
            @Parameter(name = "latitude", description = "위도"),
            @Parameter(name = "longitude", description = "경도")
    })
    @GetMapping()
    public ResponseEntity<CommonResponseDto<List<TodoSimpleInfoResDto>>> calculateLocation(@AuthenticationPrincipal UserDetails principal,
                                                                                           @RequestParam("latitude") double latitude,
                                                                                           @RequestParam("longitude") double longitude)
    {
        log.info("[calculateLocation] 현재 사용자의 좌표를 입력받아, 해당하는 할일을 찾습니다.");
        String loginId = principal.getUsername();
        List<TodoSimpleInfoResDto> todoSimpleInfoResDtoList = locationService.calculateLocation(loginId,latitude,longitude);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "현재 좌표에 해당하는 할 일들을 성공적으로 조회하였습니다.",
                        todoSimpleInfoResDtoList));
    }

    @Operation(summary = "좌표 수정", description = "좌표를 수정합니다.")
    @Parameter(name = "locationId", description = "수정할 좌표의 id")
    @PutMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationResDto>> updateLocation(@PathVariable("locationId") Long locationId,
                                                                            @RequestBody LocationReqDto locationReqDto){
        log.info("[updateLocation] 좌표를 수정합니다.");
        LocationResDto locationResDto = locationService.updateLocation(locationId, locationReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "좌표 수정을 성공적으로 완료하였습니다.",
                        locationResDto));
    }

    @Operation(summary = "User-Todo-Get메서드 테스트", description = "유저의 Todo들을 잘 조회하는지 테스트")
    @GetMapping("/Test")
    public ResponseEntity<CommonResponseDto<List<TodoSimpleInfoResDto>>> getUserTodo(@AuthenticationPrincipal UserDetails principal){
        log.info("[getUserTodo] 유저 할일을 조회하는 메서드를 테스트 하기  위함");
        String loginId = principal.getUsername();
        List<TodoSimpleInfoResDto> todoSimpleInfoResDtoList = locationService.getUserTodo(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(
                        "현재 좌표에 해당하는 할 일들을 성공적으로 조회하였습니다.",
                        todoSimpleInfoResDtoList));
    }

}
