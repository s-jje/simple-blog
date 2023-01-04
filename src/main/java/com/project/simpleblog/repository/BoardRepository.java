package com.project.simpleblog.repository;

import com.project.simpleblog.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findById(Long id);

    List<Board> findAllByCategoryAndUserIdOrderByCreatedAtDesc(String category, Long userId);

    List<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

}