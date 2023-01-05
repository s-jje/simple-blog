package com.project.simpleblog.domain;

import com.project.simpleblog.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private String username;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public Reply(ReplyRequestDto replyRequestDto, String username, Long userId, Comment comment) {
        this.content = replyRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.comment = comment;
    }

    public void update(ReplyRequestDto replyRequestDto) {
        this.content = replyRequestDto.getContent();
    }

}
