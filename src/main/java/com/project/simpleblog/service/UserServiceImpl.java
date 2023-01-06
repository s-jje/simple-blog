package com.project.simpleblog.service;

import com.project.simpleblog.domain.Board;
import com.project.simpleblog.domain.Comment;
import com.project.simpleblog.domain.User;
import com.project.simpleblog.domain.UserRoleEnum;
import com.project.simpleblog.dto.DeleteUserRequestDto;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;
import com.project.simpleblog.dto.StatusResponseDto;
import com.project.simpleblog.exception.UnauthorizedBehaviorException;
import com.project.simpleblog.jwt.JwtTokenProvider;
import com.project.simpleblog.repository.BoardRepository;
import com.project.simpleblog.repository.CategoryRepository;
import com.project.simpleblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        });

        UserRoleEnum role = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()) {
            if (!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(new User(username, password, role));
    }

    @Transactional(readOnly = true)
    @Override
    public String signIn(SignInRequestDto signInRequestDto) {
        User user = userRepository.findByUsername(signInRequestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }

    @Transactional
    @Override
    public void deleteUser(DeleteUserRequestDto deleteUserRequestDto){
        String username = deleteUserRequestDto.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 아이디가 없습니다."));

        if (passwordEncoder.matches(deleteUserRequestDto.getPassword(), user.getPassword())) {
            boardRepository.deleteAllByUserId(user.getId());
            categoryRepository.deleteAllByUserId(user.getId());
            userRepository.delete(user);
            return;
        }
        throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

}
