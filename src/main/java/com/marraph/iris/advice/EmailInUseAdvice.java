package com.marraph.iris.advice;

import com.marraph.iris.exception.EmailInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailInUseAdvice {


    @ResponseBody
    @ExceptionHandler(EmailInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String emailInUseHandler(EmailInUseException exception) {
        return exception.getMessage();
    }

}