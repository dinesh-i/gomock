package com.mock.service.util;

public class StringUtil {
	public static String convertToCamelCase(String fieldName) {
		return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
	}
}