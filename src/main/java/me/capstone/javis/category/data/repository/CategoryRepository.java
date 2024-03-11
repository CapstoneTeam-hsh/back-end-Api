package me.capstone.javis.category.data.repository;

import me.capstone.javis.category.data.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>,CategoryCustomRepository {
    Optional<Category> findByName(String categoryName);
}
