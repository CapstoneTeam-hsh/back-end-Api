package me.capstone.javis.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.location.data.dto.request.LocationReqDto;
import me.capstone.javis.location.data.dto.request.UserGpsReqDto;
import me.capstone.javis.location.data.dto.response.LocationResDto;
import me.capstone.javis.location.data.repository.LocationRepository;
import me.capstone.javis.location.util.CalculateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static me.capstone.javis.location.util.CalculateUtil.calculateDistance;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private static double setDistance = 50.0;


    public LocationResDto settingLocation(LocationReqDto locationReqDto){
        Location location = Location.builder()
                .name(locationReqDto.name())
                .latitude(locationReqDto.latitude())
                .longitude(locationReqDto.longitude())
                .build();
        return LocationResDto.toDto(locationRepository.save(location));
    }

    public List<LocationResDto> calculateLocation(String loginId, UserGpsReqDto userGpsReqDto)
    {
        List<Location> locationList = new ArrayList<>();
        List<LocationResDto> locationResDtoList = new ArrayList<>();
        locationList = locationRepository.findAll();

        for (Location location : locationList){
            double distance = calculateDistance(userGpsReqDto.latitude(),userGpsReqDto.longitude(),location.getLatitude(),location.getLongitude());
            if (distance <= setDistance) {
                // 거리가 distance threshold 내에 있는 위치를 결과 리스트에 추가
                locationResDtoList.add(LocationResDto.builder()
                                .id(location.getId())
                                .name(location.getName())
                                .latitude(location.getLatitude())
                                .longitude(location.getLongitude())
                        .build());
            }
        }
        return locationResDtoList;
    }
}
