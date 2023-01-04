package com.project.simpleblog.controller;

import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import com.project.simpleblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryResponseDto registerCategory(@RequestBody CategoryRequestDto categoryRequestDto, HttpServletRequest request) {
        return categoryService.register(categoryRequestDto, request);
    }

    @GetMapping("/categories/{categoryName}")
    public CategoryResponseDto getCategoryName(@PathVariable("categoryName") String categoryName) {
        return categoryService.getCategoryByName(categoryName);
    }


}