package me.capstone.javis.teamtodo.data.repository;

import me.capstone.javis.teamtodo.data.domain.TeamTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamTodoRepository extends JpaRepository<TeamTodo,Long>, TeamTodoCustomRepository{
}
