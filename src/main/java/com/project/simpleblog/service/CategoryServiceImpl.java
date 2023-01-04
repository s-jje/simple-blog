package com.project.simpleblog.service;

import com.project.simpleblog.domain.Category;
import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import com.project.simpleblog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryResponseDto register(CategoryRequestDto categoryRequestDto, HttpServletRequest request) {

        Category category = categoryRepository.saveAndFlush(new Category(categoryRequestDto));

        return new CategoryResponseDto(category);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponseDto getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new NoSuchElementException("해당 카테고리는 존재하지 않습니다.")).toResponseDto();
    }
}
