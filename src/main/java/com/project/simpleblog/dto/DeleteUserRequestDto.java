package com.project.simpleblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteUserRequestDto {
    private String username;
    private String password;

}
