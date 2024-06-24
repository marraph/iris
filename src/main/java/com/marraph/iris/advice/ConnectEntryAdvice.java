package com.marraph.iris.advice;

import com.marraph.iris.exception.ConnectEntryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class ConnectEntryAdvice {

    @ResponseBody
    @ExceptionHandler(ConnectEntryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String connectEntryHandler(ConnectEntryException exception) {
        return exception.getMessage();
    }

}