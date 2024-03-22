package me.capstone.javis.user.data.repository;

import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;

import java.util.List;

public interface UserCustomRepository {

    List<CategoryAndTodosResDto> findCategoryAndTodosByLoginId(String loginId);

    List<CategoryAndAllTodoResDto> findCategoryAndAllTodosByLoginId(String loginId);
}
