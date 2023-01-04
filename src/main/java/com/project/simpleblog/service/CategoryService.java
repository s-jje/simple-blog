package com.project.simpleblog.service;

import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CategoryService {

    CategoryResponseDto register(CategoryRequestDto categoryRequestDto, HttpServletRequest request);

    CategoryResponseDto getCategoryByName(String categoryName);
}
