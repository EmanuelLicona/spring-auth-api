package com.aele.springauthapi.advice;

import com.aele.springauthapi.exception.Error;
import com.aele.springauthapi.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception e) {

        ProblemDetail errorDetail = null;

        // if (e instanceof BadCredentialsException) {
        // errorDetail = ProblemDetail
        // .forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        // }

        if (e instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        }

        if (e instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        if (e instanceof SignatureException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        errorDetail.setProperty("access_denied_reason", e.getClass().getName());

        return errorDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .errors(List.of(
                        new Error(e.getMessage())))
                .path(request.getRequestURI())
                .build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        List<FieldError> filedsErrors = e.getBindingResult().getFieldErrors();

        List<Error> errorMessages = filedsErrors
                .stream()
                .map(fieldError -> new Error(
                        String.format("%s: %s",
                                fieldError.getField(),
                                fieldError.getDefaultMessage())))
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errorMessages)
                .path(request.getRequestURI())
                .build();

    }

}
