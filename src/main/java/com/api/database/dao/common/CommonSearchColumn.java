package com.api.database.dao.common;

import com.api.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommonSearchColumn {
	private static final String SEARCH_LIKE = "%";

	private static final String SELECT_COUNT = "SELECT COUNT(*)\n";

	private static final String OFF_SET_LIMIT = "\nOFFSET %s LIMIT %s ";

	public void offSetLimit(int pageNo, int pageSize, StringBuilder sql) {
		int offset = pageNo * pageSize;
		sql.append(String.format(OFF_SET_LIMIT, offset, pageSize));
	}

	public void isInteger(StringBuilder sql, Integer value, String sqlQuery) {
		if (value != null) {
			sql.append(sqlQuery);
		}
	}

	public void isLong(StringBuilder sql, Long value, String sqlQuery) {
		if (value != null) {
			sql.append(sqlQuery);
		}
	}

	public void isBoolean(StringBuilder sql, Boolean value, String sqlQuery) {
		if (value != null) {
			sql.append(sqlQuery);
		}
	}

	public void isTrue(StringBuilder sql, Boolean value, String sqlQuery) {
		if (Boolean.TRUE.equals(value)) {
			sql.append(sqlQuery);
		}
	}

	public void isString(StringBuilder sql, String value, String sqlQuery) {
		if (StringUtils.isNotBlank(value)) {
			sql.append(sqlQuery);
		}
	}

	public void isTimestamp(StringBuilder sql, Integer type, String startDate, String endDate,
							Map<Integer, String> typeMap, String dateFormat) {
		if (type == null || !typeMap.containsKey(type)) {
			return;
		}

		if (StringUtils.isNotBlank(startDate)) {
			sql.append(String.format(" AND %s >= TO_TIMESTAMP(:startDate, '%s')", typeMap.get(type), dateFormat));
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(String.format(" AND %s <= TO_TIMESTAMP(:endDate, '%s')", typeMap.get(type), dateFormat));
		}
	}

	public void isDateBetween(StringBuilder sql, String date, Map<Integer, String> typeMap, String dateFormat) {
		if (typeMap.isEmpty() || typeMap.entrySet().size() != 2) {
			return;
		}

		if (StringUtils.isNotBlank(date)) {
			sql.append(String.format(" AND %s >= TO_TIMESTAMP(:startDate, '%s')", typeMap.get(0), dateFormat));
			sql.append(String.format(" AND %s <= TO_TIMESTAMP(:endDate, '%s')", typeMap.get(1), dateFormat));
		}
	}

	public void equalDateString(StringBuilder sql, Integer type, String startDate, String endDate,
								Map<Integer, String> typeMap, String dateFormat) {
		if (type == null || !typeMap.containsKey(type)) {
			return;
		}

		if (StringUtils.isNotBlank(startDate)) {
			sql.append(String.format(" AND TO_CHAR(%s, '%s') = :startDate ", typeMap.get(type), dateFormat));
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(String.format(" AND TO_CHAR(%s, '%s') = :endDate ", typeMap.get(type), dateFormat));
		}
	}

	public void isDateNotGreaterThan(StringBuilder sql, Integer type, String startDate, String endDate,
									 Map<Integer, String> typeDateSearchMap) {
		if (type == null || !typeDateSearchMap.containsKey(type)) {
			return;
		}

		if (StringUtils.isNotBlank(startDate)) {
			sql.append(" and ").append(typeDateSearchMap.get(type)).append(" = ")
					.append("to_date(" + ":startDate" + ",'" + DateTimeUtils.Pattern.DD_MM_YYYY + "')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" and ").append(typeDateSearchMap.get(type)).append(" = ")
					.append("to_date(" + ":endDate" + ",'" + DateTimeUtils.Pattern.DD_MM_YYYY + "')");
		}
	}

	public void isList(StringBuilder sql, List<?> value, String sqlQuery) {
		if (!Objects.isNull(value) && !value.isEmpty()) {
			sql.append(sqlQuery);
		}
	}

	public void setParameterInteger(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, Integer value) {
		if (value != null) {
			query.setParameter(parameter, value);
			if (queryCount != null) {
				queryCount.setParameter(parameter, value);
			}
		}
	}

	public void setParameterLong(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, Long value) {
		if (value != null) {
			query.setParameter(parameter, value);
			if (queryCount != null) {
				queryCount.setParameter(parameter, value);
			}
		}
	}

	public void setParameterBoolean(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, Boolean value) {
		if (value != null) {
			query.setParameter(parameter, value);
			if (queryCount != null) {
				queryCount.setParameter(parameter, value);
			}
		}
	}

	public void setParameterIsTrue(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, Boolean value) {
		if (Boolean.TRUE.equals(value)) {
			query.setParameter(parameter, true);
			if (queryCount != null) {
				queryCount.setParameter(parameter, true);
			}
		}
	}

	public void setParameterString(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, String value) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(parameter)) {
			String parameterValue = value.strip().toLowerCase();
			if (value.equals(SEARCH_LIKE)) {
				parameterValue = SEARCH_LIKE + "\\" + parameterValue + SEARCH_LIKE;
			} else {
				parameterValue = SEARCH_LIKE + parameterValue + SEARCH_LIKE;
			}
			query.setParameter(parameter, parameterValue);
			if (queryCount != null) {
				queryCount.setParameter(parameter, parameterValue);
			}
		}
	}

	public void setParameterStringNotLike(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter,
										  String value) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(parameter)) {
			query.setParameter(parameter, value.strip().toLowerCase());
			if (queryCount != null) {
				queryCount.setParameter(parameter, value.strip().toLowerCase());
			}
		}
	}

	public static void setParameterDate(NativeQuery<?> query, NativeQuery<?> queryCount, Integer type, String start,
										String end, Map<Integer, String> typeMap) {
		if (type == null || !typeMap.containsKey(type)) {
			return;
		}
		if (StringUtils.isNotBlank(start)) {
			query.setParameter("startDate", start);
			if (queryCount != null) {
				queryCount.setParameter("startDate", start);
			}
		}
		if (StringUtils.isNotBlank(end)) {
			query.setParameter("endDate", end);
			if (queryCount != null) {
				queryCount.setParameter("endDate", end);
			}
		}
	}

	public void setParameterList(NativeQuery<?> query, NativeQuery<?> queryCount, String parameter, List<?> value) {
		if (value != null && !value.isEmpty()) {
			query.setParameter(parameter, value);
			if (queryCount != null) {
				queryCount.setParameter(parameter, value);
			}
		}
	}

	public void setParameterTimestamp(NativeQuery<?> query, String parameter, Timestamp value) {
		if (value != null) {
			query.setParameter(parameter, value);
		}
	}
}
