package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Reply;
import lombok.Getter;

@Getter
public class ReplyResponseDto {

    private final Long id;
    private final String content;
    private final String username;
    private final String createdAt;
    private final String modifiedAt;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.username = reply.getUsername();
        this.createdAt = reply.getCreatedAt().toString();
        this.modifiedAt = reply.getModifiedAt().toString();
    }

}
