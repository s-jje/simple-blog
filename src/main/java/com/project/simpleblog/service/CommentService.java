package com.project.simpleblog.service;

import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    CommentResponseDto register(Long boardId, CommentRequestDto commentRequestDto, HttpServletRequest request);

    CommentResponseDto update(Long boardId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request);

    StatusResponseDto delete(Long boardId, Long commentId, HttpServletRequest request);


}
