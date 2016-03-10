package com.mock.service.value.generators;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ValueGeneratorUtil {

	@Autowired
	@Qualifier("stringValueGenerator")
	private ValueGenerator<String> stringValueGenerator;

	@Autowired
	@Qualifier("defaultValueGenerator")
	private ValueGenerator<Object> defaultValueGenerator;

	@Autowired
	@Qualifier("enumValueGenerator")
	private ValueGenerator<Enum> enumValueGenerator;

	@Autowired
	@Qualifier("arrayValueGenerator")
	private ValueGenerator arrayValueGenerator;

	@Autowired
	@Qualifier("collectionValueGenerator")
	private ValueGenerator collectionValueGenerator;

	public ValueGenerator getValueGenerator(Class<?> type) {
		if (type == String.class)
			return stringValueGenerator;
		else if (type.isEnum())
			return enumValueGenerator;
		else if (type.isArray())
			return arrayValueGenerator;
		else if (Collection.class.isAssignableFrom(type))
			return collectionValueGenerator;
		else
			return defaultValueGenerator;
	}

	public boolean isSpecificGeneratorAvailable(Class<?> type) {
		return ((type == String.class) || type.isEnum() || type.isArray() || Collection.class.isAssignableFrom(type));
	}
}
