package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

public interface CommentService {

    CommentResponseDto register(Long boardId, CommentRequestDto commentRequestDto, User user);

    CommentResponseDto update(Long boardId, Long commentId, CommentRequestDto commentRequestDto, User user);

    StatusResponseDto delete(Long boardId, Long commentId, User user);

}
