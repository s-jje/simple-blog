package com.project.simpleboard.controller;

import com.project.simpleboard.dto.CommentRequestDto;
import com.project.simpleboard.dto.CommentResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;
import com.project.simpleboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")
    public CommentResponseDto registerComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.register(boardId, new CommentRequestDto(commentRequestDto.getContent()), request);
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.update(boardId, commentId, commentRequestDto, request);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public StatusResponseDto deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, HttpServletRequest request) {
        return commentService.delete(boardId, commentId, request);
    }

}
