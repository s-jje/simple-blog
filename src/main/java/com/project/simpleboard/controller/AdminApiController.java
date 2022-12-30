package com.project.simpleboard.controller;

import com.project.simpleboard.dto.SignUpRequestDto;
import com.project.simpleboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final UserService userService;

    @PostMapping("/auth/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto, HttpServletRequest request) {
        signupRequestDto.adminSignUp(request.getHeader("Authorization"));
        userService.signUp(signupRequestDto);
        return "관리자 회원가입 성공";
    }

}
