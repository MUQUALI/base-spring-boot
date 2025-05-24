package com.api.exception;

import java.io.Serial;

public class InvalidPasswordException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException(String password) {
        super(String.format("Invalid password: [%s]", password));
    }
}
