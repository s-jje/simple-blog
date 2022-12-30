package com.project.simpleboard.service;

import com.project.simpleboard.dto.BoardRequestDto;
import com.project.simpleboard.dto.BoardResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoardService {

    BoardResponseDto register(BoardRequestDto boardRequestDto, HttpServletRequest request);

    BoardResponseDto getBoard(Long id);

    List<BoardResponseDto> getBoards();

    BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request);

    StatusResponseDto delete(Long id, HttpServletRequest request);

}
