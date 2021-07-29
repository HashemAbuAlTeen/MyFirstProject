package com.example.demo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IncorrectUsernameOrPasswordAdvice {

    @ResponseBody
    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String incorrectUsernameOrPasswordHandler (IncorrectUsernameOrPasswordException ex){
        return ex.getMessage();
    }

}
