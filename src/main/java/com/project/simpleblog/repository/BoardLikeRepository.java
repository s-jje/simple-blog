package com.project.simpleblog.repository;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.BoardLike;
import com.project.simpleblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUsername(HttpServletRequest request);

}
