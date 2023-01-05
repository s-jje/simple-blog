package com.project.simpleblog.domain;

import com.project.simpleblog.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="reply" )
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCount;

    public Reply(ReplyRequestDto replyRequestDto, String username, Long userId, Comment comment) {
        this.content = replyRequestDto.getContent();
        this.username = username;
        this.userId = userId;
        this.comment = comment;
        this.likeCount = 0;
    }

    public void update(ReplyRequestDto replyRequestDto) {
        this.content = replyRequestDto.getContent();
    }

    public void updateLikeReCount(boolean checklike){
        likeCount += checklike ? 1:-1;
        if(likeCount < 0) likeCount = 0;
    }

}
