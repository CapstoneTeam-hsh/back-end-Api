package me.capstone.javis.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.location.data.dto.request.LocationReqDto;
import me.capstone.javis.location.data.dto.response.LocationResDto;
import me.capstone.javis.location.data.repository.LocationRepository;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import me.capstone.javis.todo.data.repository.TodoRepository;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    private static double setDistance = 50.0;


    public LocationResDto getOneLocation(Long locationId){
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new CustomException(ExceptionCode.LOCATION_NOT_FOUND));

        return LocationResDto.toDto(location);
    }

    public LocationResDto settingLocation(LocationReqDto locationReqDto){
        Location location = Location.builder()
                .name(locationReqDto.name())
                .latitude(locationReqDto.latitude())
                .longitude(locationReqDto.longitude())
                .build();
        return LocationResDto.toDto(locationRepository.save(location));
    }

    @Transactional(readOnly = true)
    public List<TodoSimpleInfoResDto> calculateLocation(String loginId,double latitude, double longitude)
    {
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));
        List<TodoSimpleInfoResDto> todoSimpleList = todoRepository.findTodoListByUser(user);
        List<TodoSimpleInfoResDto> resultTodoList = new ArrayList<>();

        for (TodoSimpleInfoResDto todoSimple : todoSimpleList){
            System.out.println(todoSimple);
            double distance = calculateDistance(latitude, longitude,todoSimple.latitude(),todoSimple.longitude());
            if (distance <= setDistance) {
                // 거리가 distance threshold 내에 있는 위치를 결과 리스트에 추가
                resultTodoList.add(todoSimple);
            }
        }

        return resultTodoList;
    }

    @Transactional(readOnly = true)
    public List<TodoSimpleInfoResDto> getUserTodo(String loginId)
    {
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));
        List<TodoSimpleInfoResDto> todoSimpleList = todoRepository.findTodoListByUser(user);
        return todoSimpleList;
    }

    public LocationResDto updateLocation(Long locationId, LocationReqDto locationReqDto){
        Location updateLocation = locationRepository.findById(locationId).orElseThrow(()->new CustomException(ExceptionCode.LOCATION_NOT_FOUND));

        if (locationReqDto.name() != null && !locationReqDto.name().isEmpty())
        {
            updateLocation.updateName(locationReqDto.name());
        }

        if (!Double.isNaN(locationReqDto.latitude()))
        {
            updateLocation.updateLatitude(locationReqDto.latitude());
        }

        if (!Double.isNaN(locationReqDto.longitude()))
        {
            updateLocation.updateLongitude(locationReqDto.longitude());
        }

        return LocationResDto.toDto(updateLocation);
    }

    public void deleteLocation(Long locationId)
    {
        Location location = locationRepository.findById(locationId).orElseThrow(()->new CustomException(ExceptionCode.LOCATION_NOT_FOUND));
        locationRepository.deleteById(locationId);
    }

}
