package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.BoardLike;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.repository.BoardLikeRepository;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.UserRepository;
import com.project.simpleblog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;


    @Transactional
    public String updateBoardLike(Long Id, User user){
        boolean checklike = false;

        Board board = boardRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        for(BoardLike boardLike: board.getBoardLikeList()) {
            if (boardLike.getUsername().equals(user.getUsername())){
                boardLikeRepository.delete(boardLike);
                checklike = false;
                board.updateLikeCount(checklike);
                return "좋아요 해제";
            }
        }
        boardLikeRepository.save(new BoardLike(board,user.getUsername()));
        checklike = true;
        board.updateLikeCount(checklike);
        return  "좋아요 등록";

    }
}
