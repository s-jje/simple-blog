package com.project.simpleblog.controller;

import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public BoardResponseDto registerBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.register(new BoardRequestDto(boardRequestDto.getTitle(), boardRequestDto.getContent()), userDetails.getUser());
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable("id") Long id) {
        return boardService.getBoard(id);
    }

    @PatchMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteBoard(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.delete(id, userDetails.getUser());
    }

}