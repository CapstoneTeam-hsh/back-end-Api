package me.capstone.javis.location.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.dto.CommonResponseDto;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.location.data.dto.request.LocationReqDto;
import me.capstone.javis.location.data.dto.response.LocationResDto;
import me.capstone.javis.location.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name ="[Location] Location API", description = "좌표 생성, 좌표 조회")
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;

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

}
