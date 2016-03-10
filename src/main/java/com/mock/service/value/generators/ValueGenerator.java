package com.mock.service.value.generators;

import java.lang.reflect.Type;
import java.util.Map;

public interface ValueGenerator<T> {

	Object getValue(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap, Class<T> type, Type genericParameterType)
			throws NoSuchMethodException;

	Object getValue(Class<T> type) throws NoSuchMethodException;

}