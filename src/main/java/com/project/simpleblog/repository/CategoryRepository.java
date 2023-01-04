package com.project.simpleblog.repository;

import com.project.simpleblog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUserIdAndCategory(Long userId, String category);

    List<Category> findAllByUserIdOrderByCategory(Long userId);

}
