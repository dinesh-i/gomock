package com.mock.service.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TypeMapUtil {

	public static boolean isTypeMapValueAvailable(Map<String, Object> typeMap, Class<?> type) {
		return getTypeMapValue(typeMap, type) != null;
	}

	public static Object getTypeMapValue(Map<String, Object> typeMap, Class<?> type) {
		if ((null == type) || (null == typeMap) || (typeMap.entrySet().size() == 0))
			return null;

		Set<Entry<String, Object>> entrySet = typeMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			if ((null != entry) && StringUtils.isNotEmpty(entry.getKey()) && (null != entry.getValue())) {
				if (StringUtils.equals(entry.getKey(), type.getName()))
					return entry.getValue();
			}
		}

		return null;
	}
}
