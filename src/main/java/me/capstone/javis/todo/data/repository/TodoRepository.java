package me.capstone.javis.todo.data.repository;

import me.capstone.javis.todo.data.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long>, TodoCustomRepository {
}
