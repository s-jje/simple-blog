package com.project.simpleblog.service;

import com.project.simpleblog.domain.*;
import com.project.simpleblog.repository.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentRepository commentRepository;

    private final  CommentLikeRepository commentLikeRepository;

    @Transactional
    public String updateBoardLike(Long Id,String username){
        boolean checklike = false;

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        for(BoardLike boardLike: board.getBoardLikeList()) {
            if (boardLike.getUsername().equals(username)){
                boardLikeRepository.delete(boardLike);
                checklike = false;
                board.updateLikeCount(checklike);
                return "좋아요 해제";
            }
        }
        boardLikeRepository.save(new BoardLike(board,username));
        checklike = true;
        board.updateLikeCount(checklike);
        return  "좋아요 등록";

    }

}
