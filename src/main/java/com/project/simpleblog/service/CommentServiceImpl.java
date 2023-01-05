package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.Comment;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CommentResponseDto register(Long boardId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.save(new Comment(commentRequestDto, user.getUsername(), user.getId(), board));
        return new CommentResponseDto(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto update(Long boardId, Long commentId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(comment.getUserId())) {
            comment.update(commentRequestDto);
            return new CommentResponseDto(comment);
        }
        throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
    }

    @Transactional
    @Override
    public StatusResponseDto delete(Long boardId, Long commentId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(comment.getUserId())) {
            commentRepository.deleteById(commentId);
            return new StatusResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
        }
        throw new UnauthorizedBehaviorException("작성자만 삭제할 수 있습니다.");
    }

}
