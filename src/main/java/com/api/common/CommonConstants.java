package com.api.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

	// Common value delete_flag
	public static final Boolean DELETE_TRUE = true;
	public static final Boolean DELETE_FALSE = false;

	// API response
	public static final Integer API_COMPLETED = 1;
	public static final Integer API_NONE_COMPLETE = 0;
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	public static final String LOGGED_OUT_SUCCESS = "User has successfully logged out from the system!";
	public static final String PASSWORD_ERROR = "Password incorrect.";
	public static final String RECORD_NOT_FOUND = "The %s is not found.";
	public static final String EMAIL_NOT_FOUND = "Email %s is not found.";
	public static final String INPUT_INCORRECT = "The input %s is incorrect.";
	public static final String INPUT_INCORRECT_LENGTH = "The input %s does not match the specified length.";
	public static final String VALID_PASSWORD = "The %s minimum 8 and maximum 20 characters, with uppercase characters, special characters";
	public static final String VALIDATOR_THE_FIELD = "The %s can't empty";
	public static final String VALUE_MUST_BE = "Value %s must be either %s .";
	public static final String OTP_EXPIRED = "The otp code has expired";
	public static final String NON_AUTH = "non authorize";
	public static final String INPUT_REGEX_NOT_MATCH = "The format of %s is invalid";
	public static final String INVALID_COORDINATE_FORMAT = "The request coordinate's format does not match. Expected 'latitude, longitude'";
	public static final String BAD_REQUEST_VALID = "The %s's value is not valid and may cause conflict";
	public static final String INVALID_NUMBER_FORMAT = "The %s value is not in number format";
	public static final String INVALID_PHONE_FORMAT = "The %s value is not a phone number";
	public static final String INVALID_TOKEN = "Invalid token";

	// Validation message
	public static final String VALIDATION_NOT_NULL = "The field %s can not be left empty";
	public static final String VALIDATION_MAX_SIZE_2 = "The field length can not exceed 2 characters";
	public static final String VALIDATION_MAX_SIZE_3 = "The field length can not exceed 3 characters";
	public static final String VALIDATION_MAX_SIZE_5 = "The field length can not exceed 5 characters";
	public static final String VALIDATION_MAX_SIZE_6 = "The field length can not exceed 6 characters";
	public static final String VALIDATION_MAX_SIZE_7 = "The field length can not exceed 7 characters";
	public static final String VALIDATION_MAX_SIZE_9 = "The field length can not exceed 9 characters";
	public static final String VALIDATION_MAX_SIZE_10 = "The field length can not exceed 10 characters";
	public static final String VALIDATION_MAX_SIZE_14 = "The field length can not exceed 14 characters";
	public static final String VALIDATION_MAX_SIZE_15 = "The field length can not exceed 15 characters";
	public static final String VALIDATION_MAX_SIZE_20 = "The field length can not exceed 20 characters";
	public static final String VALIDATION_MAX_SIZE_30 = "The field length can not exceed 30 characters";
	public static final String VALIDATION_MAX_SIZE_32 = "The field length can not exceed 32 characters";
	public static final String VALIDATION_MAX_SIZE_50 = "The field length can not exceed 50 characters";
	public static final String VALIDATION_MAX_SIZE_60 = "The field length can not exceed 60 characters";
	public static final String VALIDATION_MAX_SIZE_100 = "The field length can not exceed 100 characters";
	public static final String VALIDATION_MAX_SIZE_120 = "The field length can not exceed 120 characters";
	public static final String VALIDATION_MAX_SIZE_200 = "The field length can not exceed 200 characters";
	public static final String VALIDATION_MAX_SIZE_250 = "The field length can not exceed 250 characters";
	public static final String VALIDATION_MAX_SIZE_255 = "The field length can not exceed 255 characters";
	public static final String VALIDATION_MAX_SIZE_300 = "The field length can not exceed 300 characters";
	public static final String VALIDATION_MAX_SIZE_2048 = "The field length can not exceed 2048 characters";
	public static final String VALIDATION_MAX_SIZE_2000 = "The field length can not exceed 2000 characters";

	// Size validation
	public static final int MAX_2 = 2;
	public static final int MAX_3 = 3;
	public static final int MAX_5 = 5;
	public static final int MAX_6 = 6;
	public static final int MAX_7 = 7;
	public static final int MAX_9 = 9;
	public static final int MAX_10 = 10;
	public static final int MAX_14 = 14;
	public static final int MAX_15 = 15;
	public static final int MAX_20 = 20;
	public static final int MAX_30 = 30;
	public static final int MAX_32 = 32;
	public static final int MAX_50 = 50;
	public static final int MAX_60 = 60;
	public static final int MAX_100 = 100;
	public static final int MAX_120 = 120;
	public static final int MAX_200 = 200;
	public static final int MAX_250 = 250;
	public static final int MAX_255 = 255;
	public static final int MAX_300 = 300;
	public static final int MAX_2048 = 2048;
	public static final int MAX_2000 = 2000;

	// image thumbnail
	public static final Integer DEFAULT_TARGET_WIDTH = 200;
	public static final Integer DEFAULT_TARGET_HEIGHT = 200;

	// token
	public static final String BEARER_PREFIX = "Bearer ";
}
