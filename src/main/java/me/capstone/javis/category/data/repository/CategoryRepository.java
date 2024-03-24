package me.capstone.javis.category.data.repository;

import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.user.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>,CategoryCustomRepository {
    Optional<Category> findByName(String categoryName);

    List<Category> findByUser(User user);

    Optional<Category> findByNameAndUser(String name, User user);
}
