package com.project.simpleblog.domain;

import com.project.simpleblog.dto.CommentRequestDto;
import com.project.simpleblog.dto.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @OrderBy("createdAt desc")
    private final List<Reply> replyList = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, String username, Long userId, Board board) {
        this.content = commentRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.board = board;
    }

    public CommentResponseDto toResponseDto() {
        return new CommentResponseDto(id, content, username, getCreatedAt().toString(), getModifiedAt().toString(), replyList.stream().map(Reply::toResponseDto).collect(Collectors.toList()));
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

}
