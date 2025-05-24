package com.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
@Setter
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private Integer statusCode;
    private String message;

}
