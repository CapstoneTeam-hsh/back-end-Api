package me.capstone.javis.user.data.repository;

import me.capstone.javis.user.data.dto.response.CategoryAndTodosResDto;

import java.util.List;

public interface UserCustomRepository {

    List<CategoryAndTodosResDto> findCategoryAndTodosByLoginId(String loginId);
}
