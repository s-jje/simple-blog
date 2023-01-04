package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String category;
    private String content;
    private String username;
    private String createdAt;
    private String modifiedAt;
    private List<CommentResponseDto> commentList;

    private Integer likeCount;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.content = board.getContent();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt().toString();
        this.modifiedAt = board.getModifiedAt().toString();
        this.commentList = board.getCommentList().stream().map(Comment::toResponseDto).collect(Collectors.toList());
        this.likeCount = board.getLikeCount();
    }

}
