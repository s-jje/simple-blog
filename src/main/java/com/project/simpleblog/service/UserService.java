package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void signUp(SignUpRequestDto signupRequestDto);

    String signIn(SignInRequestDto signInRequestDto);

}
