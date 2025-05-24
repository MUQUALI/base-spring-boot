package com.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

@Getter
@Setter
public class BadRequestException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Integer code;

    public BadRequestException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }
}
