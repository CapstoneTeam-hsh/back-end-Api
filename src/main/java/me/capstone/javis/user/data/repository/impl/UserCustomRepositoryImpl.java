package me.capstone.javis.user.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.user.data.dto.response.calendar.AllTodoResDto;
import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TeamAndTeamTodosResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TeamTodoIdAndNameDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TodoIdAndNameResDto;
import me.capstone.javis.user.data.repository.UserCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.capstone.javis.category.data.domain.QCategory.category;
import static me.capstone.javis.team.data.domain.QTeam.team;
import static me.capstone.javis.teamtodo.data.domain.QTeamTodo.teamTodo;
import static me.capstone.javis.todo.data.domain.QTodo.todo;
import static me.capstone.javis.user.data.domain.QUser.user;
import static me.capstone.javis.userteam.domain.QUserTeam.userTeam;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

        private final JPAQueryFactory jpaQueryFactory;

        /*
        * 전체적으로 구현은 되는것 같은데 너무 비효율적이고 더럽다.
        * 수정을 하는 시간을 가져야 할 듯.
        */


        // /users//homepage/category 유저의 카테고리들과 각 카테고리에 해당하는 투두들 리턴
        @Override
        public List<CategoryAndTodosResDto> findCategoryAndTodosByLoginId(String loginId) {
                List<Tuple> userHomeCategory = jpaQueryFactory
                        .select(category.id, category.name)
                        .from(user)
                        .join(category).on(user.id.eq(category.user.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();


                //카테고리 id - 카테고리 이름 의 key-value 형태로 저장
                Map<Long,String> categoryNameMap = new HashMap<>();

                userHomeCategory.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });


                // 각 카테고리에 해당하는 모든 투두를 저장할 dto 배열
                List<CategoryAndTodosResDto> categoryTodos = new ArrayList<>();

                for (Long categoryId : categoryNameMap.keySet()){
                        List<Tuple> todoIdAndTitle = jpaQueryFactory
                                .select(category.id, category.name,todo.id, todo.title,todo.startLine,todo.deadLine)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();

                        //stream 사용으로 수정
                        List<TodoIdAndNameResDto> todoList = todoIdAndTitle.stream().map(
                                        tuple -> TodoIdAndNameResDto.builder()
                                                .todoId(tuple.get(todo.id))
                                                .title(tuple.get(todo.title))
                                                .startLine(tuple.get(todo.startLine))
                                                .deadLine(tuple.get(todo.deadLine))
                                                .build())
                                .toList();

                        //이부분 모호한데 나중에 수정해보자
                        String categoryName = todoIdAndTitle.isEmpty() ? categoryNameMap.get(categoryId) : todoIdAndTitle.get(0).get(category.name);

                        categoryTodos.add(CategoryAndTodosResDto.builder()
                                        .categoryId(categoryId)
                                        .categoryName(categoryName)
                                        .todoList(todoList)
                                        .build());

                }
                return categoryTodos;
        }

        //users/calendar 유저의 카테고리들과 각 카테고리에 해당하는 투두 전체 리턴
        @Override
        public List<CategoryAndAllTodoResDto> findCategoryAndAllTodosByLoginId(String loginId) {
                List<Tuple> userCalendarCategory = jpaQueryFactory
                        .select(category.id, category.name)
                        .from(user)
                        .join(category).on(user.id.eq(category.user.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();

                //카테고리 id - 카테고리 이름 의 key-value 형태로 저장
                Map<Long,String> categoryNameMap = new HashMap<>();

                userCalendarCategory.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });

                // 각 카테고리에 해당하는 모든 투두를 저장할 dto 배열
                List<CategoryAndAllTodoResDto> categoryAllTodos = new ArrayList<>();

                for (Long categoryId : categoryNameMap.keySet()){
                        List<Tuple> allTodos = jpaQueryFactory
                                .select(category.name,todo.id, todo.title, todo.contents, todo.completed, todo.startLine, todo.deadLine)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        //stream 사용으로 수정
                        List<AllTodoResDto> allTodoList = allTodos.stream().map(
                                tuple -> AllTodoResDto.builder()
                                        .todoId(tuple.get(todo.id))
                                        .title(tuple.get(todo.title))
                                        .contents(tuple.get(todo.contents))
                                        .completed(tuple.get(todo.completed))
                                        .startLine(tuple.get(todo.startLine))
                                        .deadLine(tuple.get(todo.deadLine))
                                        .build())
                                .toList();
                                //collect(Collectors.toList()) 랑 같은 의미.


                        //이부분 모호한데 나중에 수정해보자
                        String categoryName = allTodos.isEmpty() ? categoryNameMap.get(categoryId) : allTodos.get(0).get(category.name);

                        categoryAllTodos.add(CategoryAndAllTodoResDto.builder()
                                .categoryId(categoryId)
                                .categoryName(categoryName)
                                .allTodoList(allTodoList)
                                .build());
                }
                return categoryAllTodos;
        }
        
        //users/homepage/team 그룹 과 해당 그룹의 투두들을 리턴
        @Override
        public List<TeamAndTeamTodosResDto> findTeamAndTeamTodosByLoginId(String loginId) {
                List<Tuple>  userHomeTeam = jpaQueryFactory
                        .select(team.id, team.name)
                        .from(user)
                        .join(userTeam).on(user.id.eq(userTeam.user.id))
                        .join(team).on(userTeam.team.id.eq(team.id))
                        .where(user.loginId.eq(loginId))
                        .fetch();


                Map<Long,String> teamNameMap = new HashMap<>();

                userHomeTeam.forEach(tuple ->{
                        Long teamId = tuple.get(team.id);
                        String teamName = tuple.get(team.name);
                        teamNameMap.put(teamId,teamName);
                });

                List<TeamAndTeamTodosResDto> teamAndTeamTodo = new ArrayList<>();

                for (Long teamId : teamNameMap.keySet()){
                        List<Tuple> teamTodoIdAndTitle = jpaQueryFactory
                                .select(team.id, team.name, teamTodo.id, teamTodo.title, teamTodo.startLine, teamTodo.deadLine)
                                .from(team)
                                .join(teamTodo).on(team.id.eq(teamTodo.team.id))
                                .where(team.id.eq(teamId))
                                .fetch();

                        //stream 사용으로 수정
                        List<TeamTodoIdAndNameDto> teamTodoList = teamTodoIdAndTitle.stream()
                                .map(tuple -> TeamTodoIdAndNameDto.builder()
                                        .teamTodoId(tuple.get(teamTodo.id))
                                        .teamTodoTitle(tuple.get(teamTodo.title))
                                        .startLine(tuple.get(teamTodo.startLine))
                                        .deadLine(tuple.get(teamTodo.deadLine))
                                        .build())
                                .toList();

                        //이부분 모호한데 나중에 수정해보자
                        String teamName = teamTodoList.isEmpty() ? teamNameMap.get(teamId) : teamTodoIdAndTitle.get(0).get(team.name);

                        teamAndTeamTodo.add(TeamAndTeamTodosResDto.builder()
                                .teamId(teamId)
                                .teamName(teamName)
                                .teamTodoList(teamTodoList)
                                .build());

                }
                return teamAndTeamTodo;
        }


}
