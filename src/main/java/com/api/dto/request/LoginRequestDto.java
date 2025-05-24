package com.api.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {
	private String username;
	private String password;
}
