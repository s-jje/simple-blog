package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.BoardLike;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.repository.BoardLikeRepository;
import com.project.simpleblog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardLikeService implements LikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    @Override
    public String likeOrUnlike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        for (BoardLike boardLike : board.getBoardLikeList()) {
            if (boardLike.getUsername().equals(user.getUsername())) {
                boardLikeRepository.delete(boardLike);
                board.updateLikeCount(false);
                return "좋아요 해제";
            }
        }
        boardLikeRepository.save(new BoardLike(board, user.getUsername()));
        board.updateLikeCount(true);
        return "좋아요 등록";

    }

}
