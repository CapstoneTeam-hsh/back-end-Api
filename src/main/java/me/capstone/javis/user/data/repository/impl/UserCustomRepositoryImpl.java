package me.capstone.javis.user.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.user.data.dto.response.calendar.AllTodoResDto;
import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TodoIdAndNameResDto;
import me.capstone.javis.user.data.repository.UserCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import static me.capstone.javis.category.data.domain.QCategory.category;
import static me.capstone.javis.todo.data.domain.QTodo.todo;
import static me.capstone.javis.user.data.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

        private final JPAQueryFactory jpaQueryFactory;


        @Override
        public List<CategoryAndTodosResDto> findCategoryAndTodosByLoginId(String loginId) {
                //해당 튜플에, 유저 id, 유저 이름, 유저 프로필 사진 id, 앨범 id, 앨범 이름이 들어가있다.
                List<Tuple> userHomeCategory = jpaQueryFactory
                        .select(category.id, category.name)
                        .from(user)
                        .join(category).on(user.id.eq(category.user.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();

                Set<Long> idSet = new HashSet<>();
                List<Tuple> distinctTuples = new ArrayList<>();

                for (Tuple tuple : userHomeCategory){
                        Long categoryId = tuple.get(category.id);
                        if(!idSet.contains(categoryId)){
                                idSet.add(categoryId);
                                distinctTuples.add(tuple);
                        }
                }

                List<Long> categoryList = new ArrayList<>();
                Map<Long,String> categoryNameMap = new HashMap<>();

                distinctTuples.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryList.add(categoryMappingId);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });

                List<CategoryAndTodosResDto> categoryTodos = new ArrayList<>();

                for (Long categoryId : categoryList){
                        List<Tuple> todoIdAndTitle = jpaQueryFactory
                                .select(category.id, category.name,todo.id, todo.title)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        List<TodoIdAndNameResDto> todoList = new ArrayList<>();
                        todoIdAndTitle.forEach(tuple -> {
                                todoList.add(TodoIdAndNameResDto.builder()
                                        .todoId(tuple.get(todo.id))
                                        .title(tuple.get(todo.title))
                                        .build());
                                });

                        String categoryName = todoIdAndTitle.isEmpty() ? categoryNameMap.get(categoryId) : todoIdAndTitle.get(0).get(category.name);

                        categoryTodos.add(CategoryAndTodosResDto.builder()
                                        .categoryId(categoryId)
                                        .categoryName(categoryName)
                                        .todoList(todoList)
                                        .build());

                }
                return categoryTodos;
        }

        @Override
        public List<CategoryAndAllTodoResDto> findCategoryAndAllTodosByLoginId(String loginId) {
                //해당 튜플에, 유저 id, 유저 이름, 유저 프로필 사진 id, 앨범 id, 앨범 이름이 들어가있다.
                List<Tuple> userCalendarCategory = jpaQueryFactory
                        .select(category.id, category.name)
                        .from(user)
                        .join(category).on(user.id.eq(category.user.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();

                Set<Long> idSet = new HashSet<>();
                List<Tuple> distinctTuples = new ArrayList<>();

                for (Tuple tuple : userCalendarCategory){
                        Long categoryId = tuple.get(category.id);
                        if(!idSet.contains(categoryId)){
                                idSet.add(categoryId);
                                distinctTuples.add(tuple);
                        }
                }

                List<Long> categoryList = new ArrayList<>();
                Map<Long,String> categoryNameMap = new HashMap<>();

                distinctTuples.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryList.add(categoryMappingId);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });

                List<CategoryAndAllTodoResDto> categoryAllTodos = new ArrayList<>();

                for (Long categoryId : categoryList){
                        List<Tuple> allTodos = jpaQueryFactory
                                .select(category.name,todo.id, todo.title, todo.contents, todo.completed, todo.startLine, todo.deadLine)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        List<AllTodoResDto> allTodoList = new ArrayList<>();
                        allTodos.forEach(tuple -> {
                                allTodoList.add(AllTodoResDto.builder()
                                                .todoId(tuple.get(todo.id))
                                                .title(tuple.get(todo.title))
                                                .contents(tuple.get(todo.contents))
                                                .completed(tuple.get(todo.completed))
                                                .startLine(tuple.get(todo.startLine))
                                                .deadLine(tuple.get(todo.deadLine))
                                                .build());
                        });

                        String categoryName = allTodos.isEmpty() ? categoryNameMap.get(categoryId) : allTodos.get(0).get(category.name);

                        categoryAllTodos.add(CategoryAndAllTodoResDto.builder()
                                .categoryId(categoryId)
                                .categoryName(categoryName)
                                .allTodoList(allTodoList)
                                .build());

                }
                return categoryAllTodos;
        }


}
