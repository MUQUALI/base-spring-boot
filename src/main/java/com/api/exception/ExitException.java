package com.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
@Getter
@Setter
@AllArgsConstructor
public class ExitException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private int statusCode;
    private String message;
}
