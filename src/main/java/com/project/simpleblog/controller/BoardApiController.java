package com.project.simpleblog.controller;

import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.service.BoardLikeService;
import com.project.simpleblog.service.BoardService;
import com.project.simpleblog.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;
    private final JwtService jwtService;

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

    @PatchMapping("/boards/{id}/likes")
    public ResponseEntity<String> updateBoardLike(@PathVariable Long id,HttpServletRequest request){
        Claims claims = jwtService.getValidClaims(request);
        return new ResponseEntity<>(boardLikeService.updateBoardLike(id,claims.getSubject()),HttpStatus.OK);
    }



}