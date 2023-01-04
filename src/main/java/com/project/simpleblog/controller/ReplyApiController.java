package com.project.simpleblog.controller;

import com.project.simpleblog.dto.ReplyRequestDto;
import com.project.simpleblog.dto.ReplyResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/{boardId}/comments/{commentId}/replies")
    public ReplyResponseDto registerReply(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.register(commentId, new ReplyRequestDto(replyRequestDto.getContent()), userDetails.getUser());
    }

    @PatchMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.update(commentId, replyId, replyRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{boardId}/comments/{commentId}/replies/{replyId}")
    public StatusResponseDto deleteReply(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("replyId = " + replyId);
        return replyService.delete(commentId, replyId, userDetails.getUser());
    }

}
