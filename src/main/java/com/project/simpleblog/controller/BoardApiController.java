package com.project.simpleblog.controller;

import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.BoardLikeService;
import com.project.simpleblog.service.BoardService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards(Pageable pageable) {
        return boardService.getBoards(pageable);
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

    @PatchMapping("/boards/{id}/likes")
    public ResponseEntity<String> updateBoardLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(boardLikeService.updateBoardLike(id,userDetails.getUser()), HttpStatus.OK);
    }


}