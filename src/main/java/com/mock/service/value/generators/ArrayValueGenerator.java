package com.mock.service.value.generators;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mock.service.MockPopulator;
import com.mock.service.util.FieldMapUtil;
import com.mock.service.util.TypeMapUtil;

@Component
@Qualifier("arrayValueGenerator")
public class ArrayValueGenerator implements ValueGenerator<Object> {

	private static Logger LOGGER = LoggerFactory.getLogger(ArrayValueGenerator.class);

	@Autowired
	private DefaultValues defaultValues;

	@Autowired
	private MockPopulator mockPopulator;

	@Override
	public Object getValue(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap, Class<Object> type, Type genericParameterType)
			throws NoSuchMethodException {
		return getValue(fieldName, type, fieldMap, typeMap);
	}

	private Object getValue(String fieldName, Class<Object> type, Map<String, Object> fieldMap, Map<String, Object> typeMap) {
		Class<?> arrayComponentType = type.getComponentType();
		Object arrayObject = Array.newInstance(arrayComponentType, 1);

		Object arrayComponentNewInstance = null;
		try {
			arrayComponentNewInstance = getArrayComponentNewInstance(fieldName, fieldMap, typeMap, arrayComponentType);
			Array.set(arrayObject, 0, arrayComponentNewInstance);
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("Exception[{}] in creating the instance for the array of type[{}]. Skipping this field", e.getMessage(), type);
		}

		return arrayObject;
	}

	private Object getArrayComponentNewInstance(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap,
			Class<?> arrayComponentType) throws InstantiationException, IllegalAccessException {

		if (TypeMapUtil.getTypeMapValue(typeMap, arrayComponentType) != null)
			return TypeMapUtil.getTypeMapValue(typeMap, arrayComponentType);

		if (FieldMapUtil.getFieldMapValue(fieldName, fieldMap, arrayComponentType) != null)
			return FieldMapUtil.getFieldMapValue(fieldName, fieldMap, arrayComponentType);

		Object arrayComponentNewInstance;
		arrayComponentNewInstance = arrayComponentType.newInstance();
		mockPopulator.populateMockObjectWithCustomFieldMap(arrayComponentNewInstance, fieldMap, typeMap);
		return arrayComponentNewInstance;
	}

	@Override
	public Object getValue(Class<Object> type) throws NoSuchMethodException {
		return getValue(null, type, new HashMap<String, Object>(), new HashMap<String, Object>());
	}

}
