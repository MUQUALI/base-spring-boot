package com.api.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
	private String newPassword;
	private String confirmNewPassword;
	private String resetToken;
}
