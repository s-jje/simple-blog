package com.project.simpleblog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatusResponseDto {

    private final String msg;
    private final int statusCode;

}
