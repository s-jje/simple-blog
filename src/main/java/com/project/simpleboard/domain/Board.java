package com.project.simpleboard.domain;

import com.project.simpleboard.dto.BoardRequestDto;
import com.project.simpleboard.dto.BoardResponseDto;
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
public class Board extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String username;

    @NotNull
    private Long userId;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @OrderBy("createdAt desc")
    private final List<Comment> commentList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, String username, Long userId) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.username = username;
        this.userId = userId;
    }

    public BoardResponseDto toResponseDto() {
        return new BoardResponseDto(id, username, title, content, getCreatedAt().toString(), getModifiedAt().toString(), commentList.stream().map(Comment::toResponseDto).collect(Collectors.toList()));
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

}
