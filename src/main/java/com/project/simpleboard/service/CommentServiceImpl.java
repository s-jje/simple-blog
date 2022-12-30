package com.project.simpleboard.service;

import com.project.simpleboard.domain.Board;
import com.project.simpleboard.domain.Comment;
import com.project.simpleboard.domain.User;
import com.project.simpleboard.dto.CommentRequestDto;
import com.project.simpleboard.dto.CommentResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;
import com.project.simpleboard.exception.UnauthorizedBehaviorException;
import com.project.simpleboard.repository.BoardRepository;
import com.project.simpleboard.repository.CommentRepository;
import com.project.simpleboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final JwtService jwtService;

    @Transactional
    @Override
    public CommentResponseDto register(Long boardId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, user.getUsername(), user.getId(), board));

        return new CommentResponseDto(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto update(Long boardId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(comment.getUserId())) {
            comment.update(commentRequestDto);
            return comment.toResponseDto();
        } else {
            throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
        }
    }

    @Transactional
    @Override
    public StatusResponseDto delete(Long boardId, Long commentId, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(comment.getUserId())) {
            commentRepository.deleteById(commentId);
            return new StatusResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new UnauthorizedBehaviorException("작성자만 삭제할 수 있습니다.");
        }
    }

}
