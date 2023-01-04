package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Comment;
import com.project.simpleblog.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String username;
    private String createdAt;
    private String modifiedAt;
    private List<ReplyResponseDto> replyList;
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.replyList = comment.getReplyList().stream().map(Reply::toResponseDto).collect(Collectors.toList());
    }

}
