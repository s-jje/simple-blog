package com.project.simpleblog.controller;

import com.project.simpleblog.dto.CategoryRequestDto;
import com.project.simpleblog.dto.CategoryResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{username}/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getCategories(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.getCategoriesByUser(userDetails.getUser());
    }

    @PostMapping
    public CategoryResponseDto register(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.register(categoryRequestDto, userDetails.getUser());
    }

    @PutMapping("/{categoryName}")
    public CategoryResponseDto update(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable String username, @PathVariable String categoryName, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("username = " + username);
        System.out.println("categoryName = " + categoryName);
        return categoryService.update(categoryRequestDto, categoryName, userDetails.getUser());
    }

    @DeleteMapping("/{categoryName}")
    public StatusResponseDto delete(@PathVariable String username, @PathVariable String categoryName, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.delete(categoryName, userDetails.getUser());
    }

}
