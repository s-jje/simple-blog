package com.project.simpleblog.dto;

import com.project.simpleblog.domain.Board;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String title;
    private final String category;
    private final String content;
    private final String username;
    private final String createdAt;
    private final String modifiedAt;
    private final List<CommentResponseDto> commentList;
    private final Integer likeCount;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.content = board.getContent();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt().toString();
        this.modifiedAt = board.getModifiedAt().toString();
        this.commentList = board.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.likeCount = board.getLikeCount();
    }

}
