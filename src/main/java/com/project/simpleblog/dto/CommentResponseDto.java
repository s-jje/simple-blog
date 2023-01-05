package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Comment;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String content;
    private final String username;
    private final String createdAt;
    private final String modifiedAt;
    private final List<ReplyResponseDto> replyList;
    private final Integer likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.replyList = comment.getReplyList().stream().map(ReplyResponseDto::new).collect(Collectors.toList());
        this.likeCount=comment.getLikeCount();
    }

}
