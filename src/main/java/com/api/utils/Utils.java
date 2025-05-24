package com.api.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class Utils {
	public static Timestamp currentTimestamp() {
		Instant instant = Instant.now();
		return Timestamp.from(instant);
	}

	public static <T> T coalesce(T checkNullValue, T alternativeValue) {
		return ValidationUtils.isNullOrEmpty(checkNullValue) ? alternativeValue : checkNullValue;
	}

	private static void addScalarTypeLong(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == long.class || field.getType() == Long.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
		}
	}

	private static void addScalarTypeInteger(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == int.class || field.getType() == Integer.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
		}
	}

	private static void addScalarTypeCharacter(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == char.class || field.getType() == Character.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.CHARACTER);
		}
	}

	private static void addScalarTypeShort(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == short.class || field.getType() == Short.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.SHORT);
		}
	}

	private static void addScalarTypeDouble(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == double.class || field.getType() == Double.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
		}
	}

	private static void addScalarTypeFloat(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == float.class || field.getType() == Float.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
		}
	}

	private static void addScalarTypeBoolean(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
		}
	}

	private static void addScalarTypeString(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == String.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
		}
	}

	private static void addScalarTypeDate(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == Date.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.DATE);
		}
	}

	private static void addScalarTimestamp(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == Timestamp.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
		}
	}

	@SuppressWarnings("deprecation")
	public static <T> void addScalar(NativeQuery<?> sqlQuery, Class<T> clazz) {
		nullPointerException(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			addScalarTypeLong(field, sqlQuery);
			addScalarTypeInteger(field, sqlQuery);
			addScalarTypeCharacter(field, sqlQuery);
			addScalarTypeShort(field, sqlQuery);
			addScalarTypeDouble(field, sqlQuery);
			addScalarTypeFloat(field, sqlQuery);
			addScalarTypeBoolean(field, sqlQuery);
			addScalarTypeString(field, sqlQuery);
			addScalarTypeDate(field, sqlQuery);
			addScalarTimestamp(field, sqlQuery);
		}
		sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
	}

	private static <T> void nullPointerException(Class<T> clazz) {
		if (clazz == null) {
			throw new NullPointerException("[clazz] could not be null!");
		}
	}

	public static <T> List<T> resultExtends(Class<T> clazz, List<?> data) {
		List<T> responseList = new ArrayList<>();
		for (Object obj : data) {
			Object[] values = (Object[]) obj;
			T responseObj;
			try {
				responseObj = clazz.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException
					 | NoSuchMethodException e) {
				logStackTrace(e, log);
				continue;
			}
			addField(responseObj, clazz, values);
			responseList.add(responseObj);
		}
		return responseList;
	}

	private static <T> void addField(T responseObj, Class<T> responseDTO, Object[] values) {
		Field[] allFields = null;
		Class<?> superClass = responseDTO.getSuperclass();
		if (superClass == Object.class || superClass == null) {
			allFields = responseDTO.getDeclaredFields();
		} else {
			Field[] declaredFields = responseDTO.getDeclaredFields();
			Field[] supperDeclaredFields = responseDTO.getSuperclass().getDeclaredFields();
			allFields = Stream.concat(Arrays.stream(supperDeclaredFields), Arrays.stream(declaredFields))
					.toArray(Field[]::new);
		}
		for (int i = 0; i < allFields.length; i++) {
			Field field = allFields[i];
			field.setAccessible(true);
			Object value = values[i];

			addTypeLong(responseObj, field, value);
			addTypeInteger(responseObj, field, value);
			addTypeCharacter(responseObj, field, value);
			addTypeDouble(responseObj, field, value);
			addTypeFloat(responseObj, field, value);
			addTypeBoolean(responseObj, field, value);
			addTypeString(responseObj, field, value);
			addTypeDate(responseObj, field, value);
			addTimestamp(responseObj, field, value);
		}
	}

	private static <T> void setField(T responseObj, Field field, Object value) {
		try {
			field.set(responseObj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logStackTrace(e, log);
		}
	}

	private static <T> void addTypeLong(T responseObj, Field field, Object value) {
		if (field.getType() == long.class || field.getType() == Long.class) {
			if (value instanceof BigInteger bigIntegerValue) {
				value = bigIntegerValue.longValue();
			}
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeInteger(T responseObj, Field field, Object value) {
		if (field.getType() == int.class || field.getType() == Integer.class) {
			if (value instanceof BigInteger bigIntegerValue) {
				value = bigIntegerValue.intValue();
			}
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeCharacter(T responseObj, Field field, Object value) {
		if (field.getType() == char.class || field.getType() == Character.class) {
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeDouble(T responseObj, Field field, Object value) {
		if (field.getType() == double.class || field.getType() == Double.class) {
			if (value instanceof BigDecimal bigDecimalValue) {
				value = bigDecimalValue.doubleValue();
			}
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeFloat(T responseObj, Field field, Object value) {
		if (field.getType() == float.class || field.getType() == Float.class) {
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeBoolean(T responseObj, Field field, Object value) {
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeString(T responseObj, Field field, Object value) {
		if (field.getType() == String.class) {
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTypeDate(T responseObj, Field field, Object value) {
		if (field.getType() == Date.class) {
			setField(responseObj, field, value);
		}
	}

	private static <T> void addTimestamp(T responseObj, Field field, Object value) {
		if (field.getType() == Timestamp.class) {
			setField(responseObj, field, value);
		}
	}

	/**
	 * Convert the string value to the desired number type, return 0 if the string value is null
	 *
	 * @param value number value in string
	 * @param clazz desired number class
	 * @param <T>   generic type that extends from Number class
	 * @return number value in desired number class
	 */
	public static <T extends Number> T toNumberOrNullToZero(String value, Class<T> clazz) {
		if (StringUtils.hasText(value) && value.matches("^[+|-]?(\\d+)(,\\d{3})*(\\.\\d+)?$")) {
			try {
				return clazz.cast(clazz.getMethod("valueOf", String.class).invoke(clazz, value));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				logStackTrace(e, log);
			}
		}

		return clazz.cast(0);
	}

	/**
	 * log stack trace from Throwable class
	 *
	 * @param ex     throwable object
	 * @param logger logger
	 * @param <T>    throwable generic class
	 */
	public static <T extends Throwable> void logStackTrace(T ex, Logger logger) {
		final Logger localLogger = ValidationUtils.isNullOrEmpty(logger) ? logger : log;

		Throwable toLogStackE = ex;
		// If an exception occurs in a CompletableFuture thread, causing it to end exceptionally,
		// delve one level into the cause of the exception.
		if (ex.getClass().equals(CompletionException.class)) {
			localLogger.info("CompetableFuture ended exceptionally: {}", ex.getMessage());
			toLogStackE = ex.getCause();
		}

		// log the stack trace
		localLogger.info("{}", toLogStackE.getMessage());
		Arrays.stream(toLogStackE.getStackTrace())
				.map(StackTraceElement::toString)
				.forEach(stackTrace -> localLogger.info("\tat {}", stackTrace));
	}
}
