package com.mock.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mock.service.util.MethodUtil;
import com.mock.service.util.StringUtil;

@Component
public class MockPopulator {

	private static Logger LOGGER = LoggerFactory.getLogger(MockPopulator.class);

	@Autowired
	private MockValueGenerator mockValueGenerator;

	public void populateMockObject(Object obj) {
		populateMockObjectWithCustomFieldMap(obj, new HashMap<String, Object>(), new HashMap<String, Object>());
	}

	public void populateMockObjectWithCustomFieldMap(Object obj, Map<String, Object> fieldMap, Map<String, Object> typeMap) {
		Method[] declaredMethods = obj.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			String methodName = method.getName();
			if (method.getName().startsWith("set")) {
				String fieldName = getFieldName(methodName);
				try {
					Object value = mockValueGenerator.getMockValue(MethodUtil.getFirstParameterType(method), fieldName, fieldMap, typeMap,
							MethodUtil.getFirstGenericParameterType(method));
					if (null != value) {
						method.invoke(obj, value);
					} else {
						LOGGER.debug("Unable to generate default value for type[{}]", MethodUtil.getFirstParameterType(method));
					}
				} catch (Exception e) {
					LOGGER.error("Exception[{}] occurred in setting the field[{}]. Continuing with other fields", e.getMessage(), fieldName);
				}
			}
		}
	}

	private String getFieldName(String methodName) {
		return StringUtil.convertToCamelCase(methodName.replace("set", ""));
	}

}