package com.mock.service.value.generators;

import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mock.service.util.FieldMapUtil;
import com.mock.service.util.TypeMapUtil;

@Component
@Qualifier("defaultValueGenerator")
public class DefaultValueGenerator implements ValueGenerator<Object> {

	@Autowired
	private DefaultValues defaultValues;

	@Override
	public Object getValue(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap, Class<Object> type, Type genericParameterType)
			throws NoSuchMethodException {
		if (TypeMapUtil.getTypeMapValue(typeMap, type) != null)
			return TypeMapUtil.getTypeMapValue(typeMap, type);

		if (FieldMapUtil.getFieldMapValue(fieldName, fieldMap, type) != null)
			return FieldMapUtil.getFieldMapValue(fieldName, fieldMap, type);

		return getValue(type);
	}

	@Override
	public Object getValue(Class<Object> type) throws NoSuchMethodException {
		return defaultValues.getDefaultValueByType(type);
	}

}
