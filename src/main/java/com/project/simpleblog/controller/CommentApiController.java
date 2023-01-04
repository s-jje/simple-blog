package com.project.simpleblog.controller;

import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.service.BoardLikeService;
import com.project.simpleblog.service.CommentLikeService;
import com.project.simpleblog.service.CommentService;
import com.project.simpleblog.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final JwtService jwtService;

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

    @PatchMapping("/{boardId}/comments/{commentId}/likes")
    public ResponseEntity<String> updateCommentLike(@PathVariable Long boardId, @PathVariable Long commentId, HttpServletRequest request){
        Claims claims = jwtService.getValidClaims(request);
        return new ResponseEntity<>(commentLikeService.updateCommentLike(boardId,commentId,claims.getSubject()), HttpStatus.OK);
    }


}
