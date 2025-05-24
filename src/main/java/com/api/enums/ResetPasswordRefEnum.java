package com.api.enums;

public enum ResetPasswordRefEnum {
	SEND_REQUEST("req"), RESET_PASSWORD("res");

	private final String value;

	ResetPasswordRefEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
