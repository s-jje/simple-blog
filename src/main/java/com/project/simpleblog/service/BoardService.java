package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto register(BoardRequestDto boardRequestDto, User user);

    BoardResponseDto getBoard(Long id);

    List<BoardResponseDto> getBoards();

    BoardResponseDto update(Long id, BoardRequestDto requestDto, User user);

    StatusResponseDto delete(Long id, User user);

}
