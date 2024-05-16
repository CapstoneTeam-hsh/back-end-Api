package me.capstone.javis.user.data.repository;

import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TeamAndTeamTodosResDto;

import java.util.List;

public interface UserCustomRepository {

    List<CategoryAndTodosResDto> findCategoryAndTodosByLoginId(String loginId);

    List<CategoryAndAllTodoResDto> findCategoryAndAllTodosByLoginId(String loginId);

    List<TeamAndTeamTodosResDto> findTeamAndTeamTodosByLoginId(String loginId);

    List<TodoSimpleInfoResDto> findAllTeamTodosByLoginId(String loginId);
}
