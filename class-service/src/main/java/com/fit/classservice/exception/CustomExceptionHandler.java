package com.fit.classservice.exception;

import com.fit.classservice.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException ex){
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }
    // Xử lý tất cả các exception chưa được khai báo
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception ex, WebRequest req) {
        // Log error
        return new ErrorResponse(500, ex.getMessage());
    }
}