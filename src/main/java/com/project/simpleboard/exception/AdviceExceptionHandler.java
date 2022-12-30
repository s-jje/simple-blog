package com.project.simpleboard.exception;

import com.project.simpleboard.dto.StatusResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public StatusResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new StatusResponseDto(e.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({SecurityException.class, MalformedJwtException.class, ExpiredJwtException.class,
            UnsupportedJwtException.class, IllegalArgumentException.class, BadCredentialsException.class})
    public StatusResponseDto handleUnauthorized(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(UnauthorizedBehaviorException.class)
    public StatusResponseDto handleForbidden(UnauthorizedBehaviorException e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler({NoSuchElementException.class, UsernameNotFoundException.class})
    public StatusResponseDto handleNotFound(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public StatusResponseDto handleInternalServerError(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
