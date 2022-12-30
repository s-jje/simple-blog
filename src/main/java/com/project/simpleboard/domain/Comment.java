package com.project.simpleboard.domain;

import com.project.simpleboard.dto.CommentRequestDto;
import com.project.simpleboard.dto.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private String username;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public Comment(CommentRequestDto commentRequestDto, String username, Long userId, Board board) {
        this.content = commentRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.board = board;
    }

    public CommentResponseDto toResponseDto() {
        return new CommentResponseDto(id, content, username, getCreatedAt().toString(), getModifiedAt().toString());
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

}
