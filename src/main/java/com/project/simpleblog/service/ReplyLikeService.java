package com.project.simpleblog.service;

import com.project.simpleblog.domain.*;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.CommentRepository;
import com.project.simpleblog.repository.ReplyLikeRepository;
import com.project.simpleblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;


    @Transactional
    public String updateReplyLike(Long boardId, Long commentId, Long replyId, User user){
        boolean checklike = false;

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new NoSuchElementException("해당 댓글은 존재하지 않습니다"));
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->new NoSuchElementException("해당 대댓글은 존재하지 않습니다."));

        for(ReplyLike replyLike : reply.getReplyLikeList()){
            if(replyLike.getUsername().equals(user.getUsername())){
                replyLikeRepository.delete(replyLike);
                checklike = false;
                reply.updateLikeReCount(checklike);
                return "좋아요 해제";
            }
        }
        replyLikeRepository.save(new ReplyLike(reply,user.getUsername()));
        checklike = true;
        reply.updateLikeReCount(checklike);
        return "좋아요 등록";
    }
}
