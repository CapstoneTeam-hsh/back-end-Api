package me.capstone.javis.todo.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.todo.data.dto.response.TodoSimpleInfoResDto;
import me.capstone.javis.todo.data.dto.response.TodoSseResDto;
import me.capstone.javis.todo.data.repository.TodoCustomRepository;
import me.capstone.javis.user.data.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.capstone.javis.category.data.domain.QCategory.category;
import static me.capstone.javis.location.data.domain.QLocation.location;
import static me.capstone.javis.todo.data.domain.QTodo.todo;
import static me.capstone.javis.user.data.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // /location 좌표 계산 테스트
    @Override
    public List<TodoSimpleInfoResDto> findTodoListByUser(User reqUser) {

        List<Tuple> userTodoList = jpaQueryFactory
                .select(todo.id,todo.title,location.latitude,location.longitude)
                .from(user)
                .join(category).on(user.id.eq(category.user.id))
                .join(todo).on(category.id.eq(todo.category.id))
                .join(location).on(todo.location.id.eq(location.id))
                .where(user.loginId.eq(reqUser.getLoginId()))
                .fetch();

        //stream 사용으로 수정
        List<TodoSimpleInfoResDto> todoSimpleInfoResDtoList = userTodoList.stream().map(
                tuple -> TodoSimpleInfoResDto.builder()
                        .id(tuple.get(todo.id))
                        .title(tuple.get(todo.title))
                        .latitude(tuple.get(location.latitude))
                        .longitude(tuple.get(location.longitude))
                        .build()
        ).toList();

        return todoSimpleInfoResDtoList;
    }

    public List<TodoSseResDto> findTodoAndUserId(){
        List<Tuple> allTodoAndUserId= jpaQueryFactory
                .select(user.id, todo.id,todo.title,todo.deadLine)
                .from(user)
                .join(category).on(user.id.eq(category.user.id))
                .join(todo).on(todo.category.id.eq(category.id))
                .fetch();

        return allTodoAndUserId.stream()
                .map(tuple -> TodoSseResDto.builder()
                        .userId(tuple.get(user.id))
                        .todoId(tuple.get(todo.id))
                        .title(tuple.get(todo.title))
                        .deadLine(tuple.get(todo.deadLine))
                        .build())
                .toList();
    }
}
