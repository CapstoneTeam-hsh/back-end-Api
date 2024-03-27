package me.capstone.javis.todo.data.repository;

import me.capstone.javis.todo.data.domain.Todo;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import me.capstone.javis.user.data.domain.User;

import java.util.List;

public interface TodoCustomRepository {
    List<TodoSimpleInfoResDto> findTodoListByUser(User user);
}
