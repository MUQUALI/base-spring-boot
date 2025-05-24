package com.api.enums;

public enum ForgotPasswordStatusEnum {
	ACTIVE(0), DEACTIVE(1);

	private final Integer value;

	ForgotPasswordStatusEnum(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
