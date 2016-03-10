package com.mock.service.util;

import java.util.Map;

public class FieldMapUtil {

	public static Object getFieldMapValue(String fieldName, Map<String, Object> fieldMap, Class<?> type) {
		if ((fieldMap.get(fieldName) != null) && (fieldMap.get(fieldName).getClass().isAssignableFrom(type)))
			return fieldMap.get(fieldName);
		return null;
	}
}