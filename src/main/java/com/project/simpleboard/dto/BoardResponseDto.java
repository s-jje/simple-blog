package com.project.simpleboard.dto;

import com.project.simpleboard.domain.Board;
import com.project.simpleboard.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String createdAt;
    private String modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt().toString();
        this.modifiedAt = board.getModifiedAt().toString();
        this.commentResponseDtoList = board.getCommentList().stream().map(Comment::toResponseDto).collect(Collectors.toList());
    }

}
