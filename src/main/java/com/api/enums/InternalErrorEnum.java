package com.api.enums;

public enum InternalErrorEnum {
	INVALID_PASSWORD_FORMAT(1001),
	INVALID_RESET_TOKEN(1002),
	RESET_TOKEN_NOT_FOUND(1003),
	RESET_TOKEN_EXPIRED(1004),
	CONFIRM_PASSWORD_NOT_MATCH(1005);

	private final Integer value;

	InternalErrorEnum(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
