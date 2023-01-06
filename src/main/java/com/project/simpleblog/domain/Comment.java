package com.project.simpleblog.domain;

import com.project.simpleblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<CommentLike> CommentLikeList = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCount; //좋아요 개수

    public Comment(CommentRequestDto commentRequestDto, String username, Long userId, Board board) {
        this.content = commentRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.board = board;
        this.likeCount = 0;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void updateLikeCmCount(boolean checklike){
        likeCount += checklike ? 1:-1;
        if(likeCount < 0) likeCount = 0;
    }

}
