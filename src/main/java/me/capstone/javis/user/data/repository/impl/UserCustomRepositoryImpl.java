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

import java.util.*;
import java.util.stream.Collectors;

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

                Map<Long,String> categoryNameMap = new HashMap<>();

                distinctTuples.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });

                List<CategoryAndTodosResDto> categoryTodos = new ArrayList<>();

                for (Long categoryId : categoryNameMap.keySet()){
                        List<Tuple> todoIdAndTitle = jpaQueryFactory
                                .select(category.id, category.name,todo.id, todo.title,todo.startLine,todo.deadLine)
                                .from(category)
                                .join(todo).on(category.id.eq(todo.category.id))
                                .where(category.id.eq(categoryId))
                                .fetch();


                        List<TodoIdAndNameResDto> todoList = new ArrayList<>();
                        todoIdAndTitle.forEach(tuple -> {
                                todoList.add(TodoIdAndNameResDto.builder()
                                        .todoId(tuple.get(todo.id))
                                        .title(tuple.get(todo.title))
                                        .startLine(tuple.get(todo.startLine))
                                        .deadLine(tuple.get(todo.deadLine))
                                        .build());
                                });

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

                Map<Long,String> categoryNameMap = new HashMap<>();

                distinctTuples.forEach(tuple -> {
                        Long categoryMappingId = tuple.get(category.id);
                        categoryNameMap.put(categoryMappingId,tuple.get(category.name));
                });

                List<CategoryAndAllTodoResDto> categoryAllTodos = new ArrayList<>();

                for (Long categoryId : categoryNameMap.keySet()){
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


        @Override
        public List<TeamAndTeamTodosResDto> findTeamAndTeamTodosByLoginId(String loginId) {
                //해당 튜플에, 유저 id, 유저 이름, 유저 프로필 사진 id, 앨범 id, 앨범 이름이 들어가있다.
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


//                        List<TodoIdAndNameResDto> todoList = new ArrayList<>();
//                        todoIdAndTitle.forEach(tuple -> {
//                                todoList.add(TodoIdAndNameResDto.builder()
//                                        .todoId(tuple.get(todo.id))
//                                        .title(tuple.get(todo.title))
//                                        .startLine(tuple.get(todo.startLine))
//                                        .deadLine(tuple.get(todo.deadLine))
//                                        .build());
//                        });

                        //주원이가 stream을 이용하여 코드 수정을 해줬는데 오류 없으면 이 방향으로 나아가야 할듯.
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
