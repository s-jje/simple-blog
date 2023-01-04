package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtService jwtService;

    @Transactional
    @Override
    public BoardResponseDto register(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, user.getUsername(), user.getId()));

        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseDto getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다.")).toResponseDto();
    }


    @Transactional(readOnly = true)
    @Override
    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(Board::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(board.getUserId())) {
            board.update(boardRequestDto);
            return board.toResponseDto();
        } else {
            throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
        }
    }

    @Transactional
    @Override
    public StatusResponseDto delete(Long id, HttpServletRequest request) {
        Claims claims = jwtService.getValidClaims(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(board.getUserId())) {
            boardRepository.deleteById(id);
            return new StatusResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new UnauthorizedBehaviorException("작성자만 삭제할 수 있습니다.");
        }
    }
}
