package com.project.simpleboard.controller;

import com.project.simpleboard.dto.BoardRequestDto;
import com.project.simpleboard.dto.BoardResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;
import com.project.simpleboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public List<BoardResponseDto> registerPage() {
        return boardService.getBoards();
    }

    @PostMapping("/boards")
    public BoardResponseDto registerBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.register(new BoardRequestDto(boardRequestDto.getTitle(), boardRequestDto.getContent()), request);
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable("id") Long id) {
        return boardService.getBoard(id);
    }

    @PatchMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.update(id, boardRequestDto, request);
    }

    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }

}