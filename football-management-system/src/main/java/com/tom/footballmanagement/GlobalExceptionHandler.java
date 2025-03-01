package com.tom.footballmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = new HashMap<>();
        System.out.println("exception.getBindingResult(): " + exception.getBindingResult());
        System.out.println("exception.getBindingResult().getAllErrors(): " + exception.getBindingResult().getAllErrors());
        return errors;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
//        Map<String, Object> errors = new HashMap<>();
//        System.out.println("exception.getBindingResult(): " + exception.getBindingResult());
//        System.out.println("exception.getBindingResult().getAllErrors(): " + exception.getBindingResult().getAllErrors());
//        return errors;
//    }
}
