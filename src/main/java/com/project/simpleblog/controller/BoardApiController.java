package com.project.simpleblog.controller;

import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.BoardLikeService;
import com.project.simpleblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable("id") Long id) {
        return boardService.getBoard(id);
    }

    @GetMapping("/users/{username}/boards")
    public List<BoardResponseDto> getBoardsByUser(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoardsByUser(userDetails.getUser());
    }

    @GetMapping("/users/{username}/categories/{categoryName}/boards")
    public List<BoardResponseDto> getBoardsByUserAndCategory(@PathVariable String username, @PathVariable String categoryName, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoardsByUserAndCategory(categoryName, userDetails.getUser());
    }

    @PostMapping("/boards")
    public BoardResponseDto registerBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.register(new BoardRequestDto(boardRequestDto.getTitle(), boardRequestDto.getCategory(), boardRequestDto.getContent()), userDetails.getUser());
    }

    @PatchMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.delete(id, userDetails.getUser());
    }

    @PatchMapping("/boards/{id}/likes")
    public ResponseEntity<String> updateBoardLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(boardLikeService.likeOrUnlike(id, userDetails.getUser()), HttpStatus.OK);
    }

}