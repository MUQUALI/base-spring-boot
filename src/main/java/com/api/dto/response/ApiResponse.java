package com.api.dto.response;

import com.api.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {
	private int status;
	private String message;
	private Object data;

	public static ApiResponse apiResponseSuccess(Object data) {
		return new ApiResponse(HttpStatus.OK.value(), CommonConstants.SUCCESS, data);
	}

	public static ApiResponse apiResponseSuccess() {
		return new ApiResponse(HttpStatus.OK.value(), CommonConstants.SUCCESS, null);
	}

	public static ApiResponse apiResponseError() {
		return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), CommonConstants.ERROR, null);
	}
}
