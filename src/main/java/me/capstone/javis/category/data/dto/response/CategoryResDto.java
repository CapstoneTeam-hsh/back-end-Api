package me.capstone.javis.category.data.dto.response;

import lombok.Builder;
import me.capstone.javis.category.data.domain.Category;

@Builder
public record CategoryResDto(
        Long id,
        String name
){

    public static CategoryResDto toDto(Category category)
    {
        return CategoryResDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
