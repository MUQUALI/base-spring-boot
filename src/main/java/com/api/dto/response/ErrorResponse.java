package com.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
	private Integer internalStatus;

	private String errorMessage;

	private Date timestamp;

	private Map<String, String> details;

	public ErrorResponse(Integer internalStatus, Date timestamp, String message, Map<String, String> details) {
		this.setter(internalStatus, timestamp, message, details);
	}

	public ErrorResponse(Integer internalStatus, Date timestamp, String message) {
		this.setter(internalStatus, timestamp, message, null);
	}

	private void setter(Integer code, Date timestamp, String message, Map<String, String> details) {
		this.internalStatus = code;
		this.timestamp = timestamp;
		this.errorMessage = message;
		this.details = details;
	}

	public static ErrorResponse build(Integer code, String message, Map<String, String> details) {
		return new ErrorResponse(code, new Date(), message, details);
	}

	public static ErrorResponse build(Integer code, String message) {
		return new ErrorResponse(code, new Date(), message);
	}
}
