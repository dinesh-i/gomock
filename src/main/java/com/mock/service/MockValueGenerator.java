package com.mock.service;

import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mock.service.model.MockSet;
import com.mock.service.util.TypeMapUtil;
import com.mock.service.value.generators.ValueGeneratorUtil;

@Component
public class MockValueGenerator {

	@Autowired
	private ValueGeneratorUtil valueGeneratorUtil;

	@Autowired
	private MockPopulator mockPopulator;

	@Autowired
	private MockSet mockSet;

	private static Logger LOGGER = LoggerFactory.getLogger(MockValueGenerator.class);

	public Object getMockValue(Class<?> parameterType) throws NoSuchMethodException {
		return getMockValue(parameterType, null, mockSet.getDefaultFieldMap(), mockSet.getDefaultTypeMap(), null);
	}

	public Object getMockValue(Class<?> parameterType, String fieldName) throws NoSuchMethodException {
		return getMockValue(parameterType, fieldName, mockSet.getDefaultFieldMap(), mockSet.getDefaultTypeMap(), null);
	}

	@SuppressWarnings("unchecked")
	public Object getMockValue(Class<?> parameterType, String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap,
			Type genericParameterType) throws NoSuchMethodException {
		Object value = null;

		if (!valueGeneratorUtil.isSpecificGeneratorAvailable(parameterType) && !TypeMapUtil.isTypeMapValueAvailable(typeMap, parameterType)) {
			try {
				value = parameterType.newInstance();
				mockPopulator.populateMockObjectWithCustomFieldMap(value, fieldMap, typeMap);
				return value;
			} catch (Exception e) {
				LOGGER.debug("Can't instantiate type[{}] by newInstance. Attempting alternate options", parameterType);
			}
		}

		return valueGeneratorUtil.getValueGenerator(parameterType).getValue(fieldName, fieldMap, typeMap, parameterType, genericParameterType);
	}
}