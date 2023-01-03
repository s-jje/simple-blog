package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;
import com.project.simpleblog.domain.UserRoleEnum;
import com.project.simpleblog.dto.SignInRequestDto;
import com.project.simpleblog.dto.SignUpRequestDto;
import com.project.simpleblog.jwt.JwtTokenProvider;
import com.project.simpleblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void signUp(SignUpRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new BadCredentialsException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public void signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(signInRequestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtTokenProvider.AUTHORIZATION_HEADER, jwtTokenProvider.createToken(user.getUsername(), user.getRole()));
    }

}
