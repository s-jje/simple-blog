package com.project.simpleblog.exception;

import com.project.simpleblog.dto.StatusResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StatusResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new StatusResponseDto(e.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StatusResponseDto handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public StatusResponseDto handleUnauthorized(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(UnauthorizedBehaviorException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public StatusResponseDto handleForbidden(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StatusResponseDto handleNotFound(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StatusResponseDto handleInternalServerError(Exception e) {
        return new StatusResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
