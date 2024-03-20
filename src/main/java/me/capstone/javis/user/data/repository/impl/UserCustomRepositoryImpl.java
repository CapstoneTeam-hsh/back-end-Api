package me.capstone.javis.user.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.user.data.dto.response.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.TodoIdAndNameResDto;
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
                List<Tuple> tuples = jpaQueryFactory
                        .select(category.id, category.name)
                        .from(user)
                        .join(category).on(user.id.eq(category.user.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();

                Set<Long> idSet = new HashSet<>();
                List<Tuple> distinctTuples = new ArrayList<>();

                for (Tuple tuple : tuples){
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
                        List<Tuple> tuples2 = jpaQueryFactory
                                .select(category.id, category.name,todo.id, todo.title)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        List<TodoIdAndNameResDto> todoList = new ArrayList<>();
                        tuples2.forEach(tuple -> {
                                todoList.add(TodoIdAndNameResDto.builder()
                                        .todoId(tuple.get(todo.id))
                                        .title(tuple.get(todo.title))
                                        .build());
                                });

                        String categoryName = tuples2.isEmpty() ? categoryNameMap.get(categoryId) : tuples2.get(0).get(category.name);

                        categoryTodos.add(CategoryAndTodosResDto.builder()
                                        .categoryId(categoryId)
                                        .categoryName(categoryName)
                                        .todoList(todoList)
                                        .build());

                }

                return categoryTodos;

        }
}
