package com.project.simpleboard.service;

import com.project.simpleboard.dto.CommentRequestDto;
import com.project.simpleboard.dto.CommentResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    CommentResponseDto register(Long boardId, CommentRequestDto commentRequestDto, HttpServletRequest request);

    CommentResponseDto update(Long boardId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request);

    StatusResponseDto delete(Long boardId, Long commentId, HttpServletRequest request);

}
