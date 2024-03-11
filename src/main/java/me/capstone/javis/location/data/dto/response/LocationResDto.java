package me.capstone.javis.location.data.dto.response;

import lombok.Builder;
import me.capstone.javis.location.data.domain.Location;

@Builder
public record LocationResDto(

        Long id,
        String name,
        double latitude,
        double longitude
){
    public static LocationResDto toDto(Location location){
        return LocationResDto.builder()
                .id(location.getId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
