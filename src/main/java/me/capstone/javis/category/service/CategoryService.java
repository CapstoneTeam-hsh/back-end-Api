package me.capstone.javis.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.category.data.dto.response.CategoryResDto;
import me.capstone.javis.category.data.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResDto makeCategory(String name){

        Category category = Category.builder()
                .name(name)
                .build();

        return CategoryResDto.toDto(categoryRepository.save(category));
    }

    public List<String> getCategoryNames(String loginId){

        List<String> categoryNameList = categoryRepository.findCategoryName(loginId);

        return categoryNameList;
    }


}
