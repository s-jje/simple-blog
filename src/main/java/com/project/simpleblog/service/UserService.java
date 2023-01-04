package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.dto.DeleteUserRequestDto;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    void signUp(SignUpRequestDto signupRequestDto);

    void signIn(SignInRequestDto signInRequestDto, HttpServletResponse response);

    void deleteUser(DeleteUserRequestDto deleteUserRequestDto);

}
