package com.project.simpleboard.controller;

import com.project.simpleboard.dto.BoardRequestDto;
import com.project.simpleboard.dto.BoardResponseDto;
import com.project.simpleboard.dto.StatusResponseDto;
import com.project.simpleboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index", "boards", boardService.getBoards());
    }

    @GetMapping("/boards")
    public ModelAndView registerPage() {
        return new ModelAndView("board");
    }

    @PostMapping("/boards")
    public BoardResponseDto register(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.register(new BoardRequestDto(boardRequestDto.getTitle(), boardRequestDto.getContent()), request);
    }

    @GetMapping("/boards/{id}")
    public ModelAndView showBoard(@PathVariable("id") Long id, HttpServletRequest request) {
        return new ModelAndView("content", "board", boardService.getBoard(id));
    }

    @PutMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.update(id, boardRequestDto, request);
    }

    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }

}
