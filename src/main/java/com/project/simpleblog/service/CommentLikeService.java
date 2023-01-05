package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.Comment;
import com.project.simpleblog.domain.CommentLike;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.CommentLikeRepository;
import com.project.simpleblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public String updateCommentLike(Long boardId,Long commentId,User user) {
        boolean checklike = false;

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new NoSuchElementException("해당 댓글은 존재하지 않습니다"));

        for (CommentLike commentLike : comment.getCommentLikeList()) {
            if (commentLike.getUsername().equals(user.getUsername())) {
                commentLikeRepository.delete(commentLike);
                checklike = false;
                comment.updateLikeCmCount(checklike);
                return "좋아요 해제";
            }
        }
        commentLikeRepository.save(new CommentLike(comment,user.getUsername()));
        checklike = true;
        comment.updateLikeCmCount(checklike);
        return "좋아요 등록";
    }
}
