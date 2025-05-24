package com.api.utils;

import com.api.common.CommonConstants;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.regex.Pattern;

@UtilityClass
public class ValidationUtils {
	public static boolean isNullOrEmpty(Object value) {
		return Objects.isNull(value) || ObjectUtils.isEmpty(value);
	}

	public static boolean nonNullOrEmpty(Object value) {
		return !isNullOrEmpty(value);
	}

	public static String validateNotNullOrEmpty(Object value, String name) {
		if (isNullOrEmpty(value)) {
			return String.format(CommonConstants.VALIDATION_NOT_NULL, name);
		}
		return null;
	}

	public static String validateIsNumber(Object value, String name) {
		if (Boolean.FALSE.equals(Pattern.matches("\\d+", Objects.toString(value, "")))) {
			return String.format(CommonConstants.INVALID_NUMBER_FORMAT, name);
		}
		return null;
	}

	public static String validateLengthPhoneNumber(Object value, String name) {
		if (Boolean.FALSE.equals(Pattern.matches("\\d{10}", Objects.toString(value, "")))) {
			return String.format(CommonConstants.INVALID_PHONE_FORMAT, name);
		}
		return null;
	}
}
