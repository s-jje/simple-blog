package com.project.simpleblog.controller;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;
import com.project.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
        userService.signUp(signupRequestDto);
        return "회원가입 성공";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        userService.signIn(signInRequestDto, response);
        return "로그인 성공";
    }

}
