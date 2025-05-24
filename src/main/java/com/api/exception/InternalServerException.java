package com.api.exception;

import com.api.enums.InternalErrorEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Custom error contains the predefined internal status for specific error mapping
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
public class InternalServerException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/** The specific error status defined by developer */
	private final Integer internalStatus;

	/** Display message */
	private final String message;

	/** Returned data */
	private final Object data;

	public InternalServerException(InternalErrorEnum errorEnum) {
		this.internalStatus = errorEnum.value();
		this.message = null;
		this.data = null;
	}

	public InternalServerException(Integer internalStatus) {
		this.internalStatus = internalStatus;
		this.message = null;
		this.data = null;
	}

	public InternalServerException(Integer internalStatus, String message) {
		this.internalStatus = internalStatus;
		this.message = message;
		this.data = null;
	}

	public InternalServerException(Integer internalStatus, String message, Object data) {
		this.internalStatus = internalStatus;
		this.message = message;
		this.data = data;
	}
}
