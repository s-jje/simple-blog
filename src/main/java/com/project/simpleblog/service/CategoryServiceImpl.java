package com.project.simpleblog.service;

import com.project.simpleblog.domain.Category;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.CategoryAlreadyExistsException;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryResponseDto register(CategoryRequestDto categoryRequestDto, User user) {
        if (categoryRepository.findAllByUserIdOrderByCategory(user.getId()).stream().noneMatch(e -> e.getCategory().equals(categoryRequestDto.getCategory()))) {
            return categoryRepository.save(new Category(categoryRequestDto, user.getId())).toResponseDto();
        }
        throw new CategoryAlreadyExistsException("이미 존재하는 카테고리입니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponseDto> getCategoriesByUser(User user) {
        return categoryRepository.findAllByUserIdOrderByCategory(user.getId()).stream().map(Category::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryResponseDto update(CategoryRequestDto categoryRequestDto, String categoryName, User user) {
        Category category = categoryRepository.findByUserIdAndCategory(user.getId(), categoryName).orElseThrow(() -> new NoSuchElementException("해당 카테고리는 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(category.getUserId())) {
            category.update(categoryRequestDto);
            return category.toResponseDto();
        }
        throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
    }

    @Transactional
    @Override
    public StatusResponseDto delete(String categoryName, User user) {
        Category category = categoryRepository.findByUserIdAndCategory(user.getId(), categoryName).orElseThrow(() -> new NoSuchElementException("해당 카테고리는 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(category.getUserId())) {
            categoryRepository.delete(category);
            return new StatusResponseDto("카테고리 삭제 성공", HttpStatus.OK.value());
        }
        throw new UnauthorizedBehaviorException("작성자만 삭제할 수 있습니다.");
    }

}
