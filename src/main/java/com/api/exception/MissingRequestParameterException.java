package com.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
public class MissingRequestParameterException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	private final String parameterName;

	private final String parameterType;

	public MissingRequestParameterException(String parameterName, String parameterType) {
		this.parameterName = parameterName;
		this.parameterType = parameterType;
	}
}
