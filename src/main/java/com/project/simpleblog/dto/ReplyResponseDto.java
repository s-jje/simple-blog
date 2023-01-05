package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyResponseDto {

    private Long id;
    private String content;
    private String username;
    private String createdAt;
    private String modifiedAt;

    private Integer likeCount;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.username = reply.getUsername();
        this.createdAt = reply.getCreatedAt().toString();
        this.modifiedAt = reply.getModifiedAt().toString();
        this.likeCount = reply.getLikeCount();
    }

}
