package com.project.simpleblog.controller;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.DeleteUserRequestDto;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;
import com.project.simpleblog.jwt.JwtTokenProvider;
import com.project.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return "회원가입 성공";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        String token = userService.signIn(signInRequestDto);
        response.addHeader(JwtTokenProvider.AUTHORIZATION_HEADER, token);
        return "로그인 성공";
    }

    @DeleteMapping
    public String deleteUser(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUser(deleteUserRequestDto);
        return "회원탈퇴 성공";
    }

}
