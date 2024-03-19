package me.capstone.javis.todo.data.dto.response;


import lombok.Builder;
import me.capstone.javis.todo.data.domain.Todo;

@Builder
public record TodoResDto(
        Long id,
        String title,
        String contents,
        Boolean completed,
        Long categoryId,
        Long locationId
        ){
    public static TodoResDto toDto(Todo todo)
    {
        return TodoResDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .contents(todo.getContents())
                .completed(todo.getCompleted())
                .categoryId(todo.getCategory().getId())
                .locationId(todo.getLocation().getId())
                .build();
    }

}
