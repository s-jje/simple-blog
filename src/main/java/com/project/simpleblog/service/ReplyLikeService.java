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
public class ReplyLikeService implements LikeService {

    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Transactional
    @Override
    public String likeOrUnlike(Long replyId, User user){
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->new NoSuchElementException("해당 대댓글은 존재하지 않습니다."));

        for (ReplyLike replyLike : reply.getReplyLikeList()) {
            if (replyLike.getUsername().equals(user.getUsername())) {
                replyLikeRepository.delete(replyLike);
                reply.updateLikeReCount(false);
                return "좋아요 해제";
            }
        }
        replyLikeRepository.save(new ReplyLike(reply,user.getUsername()));
        reply.updateLikeReCount(true);
        return "좋아요 등록";
    }

}
