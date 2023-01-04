package com.project.simpleblog.controller;

import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")
    public CommentResponseDto registerComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.register(boardId, new CommentRequestDto(commentRequestDto.getContent()), userDetails.getUser());
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(boardId, commentId, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public StatusResponseDto deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(boardId, commentId, userDetails.getUser());
    }

}
