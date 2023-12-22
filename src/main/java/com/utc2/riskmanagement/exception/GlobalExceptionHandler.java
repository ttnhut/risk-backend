package com.utc2.riskmanagement.exception;

import com.utc2.riskmanagement.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public APIResponse handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new APIResponse(resourceNotFoundException.getMessage(), Boolean.FALSE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        List<ExceptionDTO> exceptionDTOS = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e -> {
            exceptionDTOS.add(ExceptionDTO.builder().message(e.getDefaultMessage()).path(webRequest.getDescription(false)).code(HttpStatus.BAD_REQUEST.toString())
                    .timestamp(new Date()).build());
        });

        return exceptionDTOS;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceExistException.class)
    public APIResponse handleResourceExistException(ResourceExistException resourceExistException) {
        return new APIResponse(resourceExistException.getMessage(), Boolean.FALSE);
    }
}
