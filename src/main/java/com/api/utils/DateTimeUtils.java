package com.api.utils;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class DateTimeUtils {

	@UtilityClass
	public static class Pattern {
		public static final String MM_DD_YYYY = "MM/dd/yyyy";
		public static final String DD_MM_YYYY = "dd/MM/yyyy";
		public static final String YYYY_MM_DD = "yyyy-MM-dd";
		public static final String YYYY_MM = "yyyy-MM";
		public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
		public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		public static final String HH_MM_DD_MM = "HH:mm dd/MM";
	}

	public static Date currentDateTime() {
		Instant instant = Instant.now();
		return Date.from(instant);
	}

	public static Timestamp currentTimestamp() {
		Instant instant = Instant.now();
		return Timestamp.from(instant);
	}

	public static Date stringToDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(Date date, String format) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp timestampBefore(int i) {
		Instant instant = Instant.now();
		Instant newInstant = instant.plus(Duration.ofMinutes(1));
		return Timestamp.from(newInstant);
	}

	public static Timestamp stringToTimestamp(String bookingTime, String datePattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			Date parsedDate = dateFormat.parse(bookingTime);
			return new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp convertStringToTimestamp(String format, String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date parsedDate = dateFormat.parse(date);
			return new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static String convertTimestampToString(String format, Timestamp timestamp) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(timestamp);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date truncateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// Xóa các phần thời gian như giờ, phút, giây, millisecond
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	// Phương thức này trả về ngày đầu tiên của tuần trước
	public static Date getFirstDayOfLastWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1); // Di chuyển về tuần trước
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Lấy ngày đầu tiên của tuần
		return truncateTime(calendar.getTime());
	}

	// Phương thức này trả về ngày đầu tiên của tháng trước
	public static Date getFirstDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1); // Di chuyển về tháng trước
		calendar.set(Calendar.DAY_OF_MONTH, 1); // Lấy ngày đầu tiên của tháng
		return truncateTime(calendar.getTime());
	}
}
