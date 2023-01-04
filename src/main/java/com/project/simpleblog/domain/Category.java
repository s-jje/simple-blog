package com.project.simpleblog.domain;

import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String category;

    @NotNull
    private Long userId;

    public Category(CategoryRequestDto categoryRequestDto, Long userId) {
        this.category = categoryRequestDto.getCategory();
        this.userId = userId;
    }

    public CategoryResponseDto toResponseDto() {
        return new CategoryResponseDto(category);
    }

    public void update(CategoryRequestDto categoryRequestDto) {
        this.category = categoryRequestDto.getCategory();
    }

}
