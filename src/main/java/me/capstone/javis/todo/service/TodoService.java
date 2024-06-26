package me.capstone.javis.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.category.data.repository.CategoryRepository;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.location.data.repository.LocationRepository;
import me.capstone.javis.todo.data.domain.Todo;
import me.capstone.javis.todo.data.dto.request.TodoReqDto;
import me.capstone.javis.todo.data.dto.request.TodoUpdateReqDto;
import me.capstone.javis.todo.data.dto.response.TodoResDto;
import me.capstone.javis.todo.data.repository.TodoRepository;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public TodoResDto getTodo(Long todoId){
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new CustomException(ExceptionCode.TODO_NOT_FOUND));
        return TodoResDto.toDto(todo);
    }

    public TodoResDto saveTodo(String loginId, TodoReqDto todoReqDto){
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        Category category = categoryRepository.findByNameAndUser(todoReqDto.categoryName(), user).orElseThrow(()-> new CustomException(ExceptionCode.CATEGORY_NOT_FOUND));
        Location location = locationRepository.findById(todoReqDto.locationId()).orElseThrow(()-> new CustomException(ExceptionCode.LOCATION_NOT_FOUND));

        return  TodoResDto.toDto(todoRepository.save(TodoReqDto.toEntity(todoReqDto,category,location)));
    }

    public TodoResDto updateTodo(Long todoId, TodoUpdateReqDto todoUpdateReqDto){
        Todo updateTodo = todoRepository.findById(todoId).orElseThrow(()-> new CustomException(ExceptionCode.TODO_NOT_FOUND));

        if (todoUpdateReqDto.title() != null && !todoUpdateReqDto.title().isEmpty())
        {
            updateTodo.updateTitle(todoUpdateReqDto.title());
        }
        if (todoUpdateReqDto.contents() != null && !todoUpdateReqDto.contents().isEmpty())
        {
            updateTodo.updateContent(todoUpdateReqDto.contents());
        }
        if (todoUpdateReqDto.startLine() != null && !todoUpdateReqDto.startLine().isEmpty())
        {
            updateTodo.updateStartLine(todoUpdateReqDto.startLine());
        }
        if (todoUpdateReqDto.deadLine() != null && !todoUpdateReqDto.deadLine().isEmpty())
        {
            updateTodo.updateDeadLine(todoUpdateReqDto.deadLine());
        }

        return  TodoResDto.toDto(updateTodo);
    }

    public void deleteTodo(Long todoId){
        todoRepository.deleteById(todoId);
    }

}
