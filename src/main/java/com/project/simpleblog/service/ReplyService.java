package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.ReplyRequestDto;
import com.project.simpleblog.dto.ReplyResponseDto;
import com.project.simpleblog.dto.StatusResponseDto;

public interface ReplyService {

    ReplyResponseDto register(Long commentId, ReplyRequestDto replyRequestDto, User user);

    ReplyResponseDto update(Long commentId, Long replyId, ReplyRequestDto replyRequestDto, User user);

    StatusResponseDto delete(Long commentId, Long replyId, User user);

}
