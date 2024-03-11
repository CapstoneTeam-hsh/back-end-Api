package me.capstone.javis.category.data.repository;

import java.util.List;

public interface CategoryCustomRepository {

    List<String> findCategoryName(String loginId);
}
