package com.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenRequestException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	private final String message;

	public InvalidTokenRequestException(String message) {
		this.message = message;
	}

	public InvalidTokenRequestException() {
		this.message = null;
	}
}
