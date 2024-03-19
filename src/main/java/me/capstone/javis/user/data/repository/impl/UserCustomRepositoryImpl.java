package me.capstone.javis.user.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.user.data.dto.response.CategoryAndTodosResDto;
import me.capstone.javis.user.data.repository.UserCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                distinctTuples.forEach(tuple -> {
                        categoryList.add(tuple.get(category.id));
                });

                List<CategoryAndTodosResDto> categoryTodos = new ArrayList<>();

                for (Long categoryId : categoryList){
                        List<Tuple> tuples2 = jpaQueryFactory
                                .select(category.id, category.name, todo.title)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        List<String> todoTitles = new ArrayList<>();
                        tuples2.forEach(tuple -> {
                                todoTitles.add(tuple.get(todo.title));
                                });

                        String categoryName = tuples2.get(0).get(category.name);
                        categoryTodos.add(CategoryAndTodosResDto.builder()
                                        .categoryId(categoryId)
                                        .categoryName(categoryName)
                                        .todoTitleList(todoTitles)
                                        .build());

                }

                return categoryTodos;

        }
}
