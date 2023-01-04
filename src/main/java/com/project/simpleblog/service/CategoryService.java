package com.project.simpleblog.service;

import com.project.simpleblog.domain.Category;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto register(CategoryRequestDto categoryRequestDto, User user);

    List<Category> getCategories();

    List<CategoryResponseDto> getCategoriesByUser(User user);

    CategoryResponseDto update(CategoryRequestDto categoryRequestDto, String categoryName, User user);

    StatusResponseDto delete(String category, User user);

}
