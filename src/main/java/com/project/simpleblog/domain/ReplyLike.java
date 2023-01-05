package com.project.simpleblog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReplyLike extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Reply_id", nullable = false)
    private Reply reply;
    private String username;

    @Builder
    public ReplyLike(Reply reply, String username){
        this.reply = reply;
        this.username = username;
    }

}
