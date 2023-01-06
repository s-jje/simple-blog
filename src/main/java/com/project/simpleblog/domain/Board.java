package com.project.simpleblog.domain;

import com.project.simpleblog.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    private String content;

    @NotNull
    private String username;

    @NotNull
    private Long userId;

    //좋아요기능
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<BoardLike> boardLikeList = new ArrayList<>();

    //좋아요 개수
    @Column(nullable = false)
    private Integer likeCount;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @OrderBy("createdAt desc")
    private final List<Comment> commentList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, String username, Long userId) {
        this.title = boardRequestDto.getTitle();
        this.category = boardRequestDto.getCategory();
        this.content = boardRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.likeCount = 0;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.category = boardRequestDto.getCategory();
        this.content = boardRequestDto.getContent();
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public void updateLikeCount(boolean checkLike) {
        likeCount += checkLike ? 1 : -1;
        if (likeCount < 0) likeCount = 0;
    }

}
