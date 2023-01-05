package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final String category;

    public CategoryResponseDto(Category category) {
        this.category = category.getCategory();
    }

}
