package com.project.simpleblog.service;

import com.project.simpleblog.domain.Comment;
import com.project.simpleblog.domain.Reply;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.ReplyRequestDto;
import com.project.simpleblog.dto.ReplyResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.repository.CommentRepository;
import com.project.simpleblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Override
    public ReplyResponseDto register(Long commentId, ReplyRequestDto replyRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Reply reply = replyRepository.save(new Reply(replyRequestDto, user.getUsername(), user.getId(), comment));
        return new ReplyResponseDto(reply);
    }

    @Override
    public ReplyResponseDto update(Long commentId, Long replyId, ReplyRequestDto replyRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(reply.getUserId())) {
            reply.update(replyRequestDto);
            return reply.toResponseDto();
        }
        throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
    }

    @Override
    public StatusResponseDto delete(Long commentId, Long replyId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));

        if (user.isAdmin() || user.isValidId(reply.getUserId())) {
            replyRepository.deleteById(replyId);
            return new StatusResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
        }
        throw new UnauthorizedBehaviorException("작성자만 수정할 수 있습니다.");
    }

}
