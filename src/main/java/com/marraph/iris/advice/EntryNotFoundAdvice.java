package com.marraph.iris.advice;

import com.marraph.iris.exception.EntryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public final class EntryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entryNotFoundHandler(EntryNotFoundException exception) {
        return exception.getMessage();
    }
}