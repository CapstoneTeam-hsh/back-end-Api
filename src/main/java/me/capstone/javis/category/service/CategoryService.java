package me.capstone.javis.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.category.data.dto.response.CategoryResDto;
import me.capstone.javis.category.data.repository.CategoryRepository;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public CategoryResDto getOneCategory(Long categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new CustomException(ExceptionCode.CATEGORY_NOT_FOUND));

        return CategoryResDto.toDto(category);
    }

    public CategoryResDto makeCategory(String loginId, String name){
        if(name == null) {name = "기본";}
        final String reqName = name; //스트림 내에서 람다 표현식에서 사용되는 변수는 final 또는 effectively final 이어야 한다.

        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        List<Category> categoryList = categoryRepository.findByUser(user);

        if (categoryList.stream().anyMatch(category -> category.getName().equals(reqName))) {
            throw new CustomException(ExceptionCode.DUPLICATE_CATEGORY_NAME);
        }

        Category category = Category.builder()
                .name(name)
                .user(user)
                .build();

        return CategoryResDto.toDto(categoryRepository.save(category));
    }

    public List<String> getCategoryNames(String loginId){

        List<String> categoryNameList = categoryRepository.findCategoryName(loginId);

        return categoryNameList;
    }

    public CategoryResDto updateCategoryName(Long categoryId, String name){
        Category updateCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new CustomException(ExceptionCode.CATEGORY_NOT_FOUND));

        if(name != null && !name.isEmpty()){
            updateCategory.updateName(name);
        }

        return CategoryResDto.toDto(updateCategory);
    }

    public void deleteCategory(String categoryName)
    {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new CustomException(ExceptionCode.CATEGORY_NOT_FOUND));
        categoryRepository.deleteById(category.getId());
    }


}
