package com.project.simpleblog.controller;

import com.project.simpleblog.dto.BoardResponseDto;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;
import com.project.simpleblog.jwt.JwtTokenProvider;
import com.project.simpleblog.security.UserDetailsImpl;
import com.project.simpleblog.service.BoardService;
import com.project.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final BoardService boardService;

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

    @GetMapping("/{username}/categories/{categoryName}/boards")
    public List<BoardResponseDto> getBoardsByCategory(@PathVariable String username, @PathVariable String categoryName, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoardsByCategory(categoryName, userDetails.getUser());
    }

}
