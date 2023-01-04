package com.project.simpleblog.service;

import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoardService {

    BoardResponseDto register(BoardRequestDto boardRequestDto, HttpServletRequest request);

    BoardResponseDto getBoard(Long id);

    List<BoardResponseDto> getBoards();

    BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request);

    StatusResponseDto delete(Long id, HttpServletRequest request);

}
