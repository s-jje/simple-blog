package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.BoardRequestDto;
import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public BoardResponseDto register(BoardRequestDto boardRequestDto, User user) {
        if (categoryRepository.findAllByUserIdOrderByCategory(user.getId()).stream().anyMatch(e -> e.getCategory().equals(boardRequestDto.getCategory()))) {
            return boardRepository.save(new Board(boardRequestDto, user.getUsername(), user.getId())).toResponseDto();
        }
        throw new NoSuchElementException("해당 카테고리는 존재하지 않습니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseDto getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다.")).toResponseDto();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardResponseDto> getBoards(Pageable pageable) {
        return boardRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(Board::toResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardResponseDto> getBoardsByCategory(String categoryName, User user) {
        return boardRepository.findAllByCategoryAndUserIdOrderByCreatedAtDesc(categoryName, user.getId()).stream().map(Board::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(board.getUserId())) {
            board.update(boardRequestDto);
            return board.toResponseDto();
        }
        throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
    }

    @Transactional
    @Override
    public StatusResponseDto delete(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(board.getUserId())) {
            boardRepository.deleteById(id);
            return new StatusResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        }
        throw new UnauthorizedBehaviorException("작성자만 삭제할 수 있습니다.");
    }

}
