package com.mock.service.value.generators;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DefaultValues {

	private Map<String, Object> defaultValuesMap;

	public DefaultValues() {
		this.defaultValuesMap = new HashMap<String, Object>();
		initMapWithPresets();
	}

	public DefaultValues(Map<String, Object> defaultValuesMap) {
		this.defaultValuesMap = defaultValuesMap;
		initMapWithPresets();
	}

	private void initMapWithPresets() {
		if (defaultValuesMap == null) {
			defaultValuesMap = new HashMap<String, Object>();
		}
		for (PresetDefaultValue pdv : PresetDefaultValue.values()) {
			defaultValuesMap.put(pdv.typeName, pdv.defaultValue);
		}
	}

	public Map<String, Object> getDefaultValuesMap() {
		return defaultValuesMap;
	}

	public void setDefaultValuesMap(Map<String, Object> defaultValuesMap) {
		this.defaultValuesMap = defaultValuesMap;
	}

	private enum PresetDefaultValue {
		DOUBLE(Double.class.getSimpleName(), 0.0), STRING(String.class.getSimpleName(), ""), FLOAT(Float.class.getSimpleName(), Float.MIN_VALUE), LONG(
				Long.class.getSimpleName(), Long.MIN_VALUE), SHORT(Short.class.getSimpleName(), Short.MIN_VALUE), INTEGER(Integer.class
				.getSimpleName(), 0), BOOLEAN(Boolean.class.getSimpleName(), false), BYTE(Byte.class.getSimpleName(), Byte.MIN_VALUE), CHARACTER(
				Character.class.getSimpleName(), ' '), BIGDECIMAL(BigDecimal.class.getSimpleName(), BigDecimal.ONE), PRIMITIVE_BOOLEAN(boolean.class
				.getSimpleName(), false), PRIMITIVE_BYTE(byte.class.getSimpleName(), Byte.MIN_VALUE), PRIMITIVE_CHAR(char.class.getSimpleName(), ' '), PRIMITIVE_SHORT(
				short.class.getSimpleName(), Short.MIN_VALUE), PRIMITIVE_INT(int.class.getSimpleName(), 0), PRIMITIVE_LONG(
				long.class.getSimpleName(), Long.MIN_VALUE), PRIMITIVE_FLOAT(float.class.getSimpleName(), Float.MIN_VALUE), PRIMITIVE_DOUBLE(
				double.class.getSimpleName(), 0.0);

		private String typeName;
		private Object defaultValue;

		private PresetDefaultValue(String typeName, Object defaultValue) {
			this.typeName = typeName;
			this.defaultValue = defaultValue;
		}
	}

	public Object getDefaultValueByType(Class<?> aClass) throws NoSuchMethodException {
		String simpleName = aClass.getSimpleName();
		Object defaultValue = getDefaultValuesMap().get(simpleName);
		return defaultValue;
	}

}