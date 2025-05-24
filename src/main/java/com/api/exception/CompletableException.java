package com.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CompletableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CompletableException(String exception) {
        super(exception);
    }

    public CompletableException() {
        super();
    }
}
