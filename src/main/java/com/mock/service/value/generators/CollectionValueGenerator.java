package com.mock.service.value.generators;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mock.service.MockPopulator;
import com.mock.service.MockValueGenerator;
import com.mock.service.util.FieldMapUtil;
import com.mock.service.util.TypeMapUtil;

@Component
@Qualifier("collectionValueGenerator")
public class CollectionValueGenerator implements ValueGenerator<Collection> {

	private static Logger LOGGER = LoggerFactory.getLogger(ArrayValueGenerator.class);

	@Autowired
	private DefaultValues defaultValues;

	@Autowired
	private MockPopulator mockPopulator;

	@Autowired
	private MockValueGenerator mockValueGenerator;

	@Override
	public Object getValue(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap, Class<Collection> type,
			Type genericParameterType) throws NoSuchMethodException {
		return getValue(fieldName, type, fieldMap, typeMap, genericParameterType);
	}

	@SuppressWarnings("unchecked")
	private Object getValue(String fieldName, Class<Collection> type, Map<String, Object> fieldMap, Map<String, Object> typeMap,
			Type genericParameterType) {
		Collection collectionObject = getCollection(type);

		if (collectionObject == null) {
			LOGGER.error("Can't handle the collection type[{}]. Skipping this field", type);
			return null;
		}

		try {
			// Create a default instance of the collection type in -
			// collectionComponentNewInstance
			if ((null != genericParameterType) && (genericParameterType instanceof ParameterizedType)) {
				ParameterizedType aType = (ParameterizedType) genericParameterType;
				Class<?> collectionComponentType = (Class<?>) aType.getActualTypeArguments()[0];
				LOGGER.debug("parameterArgClass = " + collectionComponentType);

				Object collectionComponentNewInstance = getCollectionComponentInstance(fieldName, fieldMap, typeMap, collectionComponentType);
				collectionObject.add(collectionComponentNewInstance);
			}
		} catch (NoSuchMethodException e) {
			LOGGER.error("Exception[{}] in creating the instance for the array of type[{}]. Skipping this field", e.getMessage(), type);
		}
		return collectionObject;
	}

	private Collection getCollection(Class<Collection> type) {
		Collection collectionObject = null;

		if (type.isAssignableFrom(List.class))
			collectionObject = new ArrayList();
		else if (type.isAssignableFrom(Set.class))
			collectionObject = new HashSet();

		return collectionObject;
	}

	private Object getCollectionComponentInstance(String fieldName, Map<String, Object> fieldMap, Map<String, Object> typeMap,
			Class<?> collectionComponentType) throws NoSuchMethodException {

		if (TypeMapUtil.getTypeMapValue(typeMap, collectionComponentType) != null)
			return TypeMapUtil.getTypeMapValue(typeMap, collectionComponentType);

		if (FieldMapUtil.getFieldMapValue(fieldName, fieldMap, collectionComponentType) != null)
			return FieldMapUtil.getFieldMapValue(fieldName, fieldMap, collectionComponentType);

		Object collectionComponentNewInstance = mockValueGenerator.getMockValue(collectionComponentType, fieldName);
		try {
			mockPopulator.populateMockObjectWithCustomFieldMap(collectionComponentNewInstance, fieldMap, typeMap);
		} catch (Exception e) {
			LOGGER.error("Exception[{}] in populating the collection component instance. Setting the current instance to collection.", e.getMessage());
		}
		return collectionComponentNewInstance;
	}

	@Override
	public Object getValue(Class<Collection> type) throws NoSuchMethodException {
		Collection collectionObject = getCollection(type);

		if (collectionObject == null) {
			LOGGER.error("Can't handle the collection type[{}]. Skipping this field", type);
			return null;
		}
		return collectionObject;
	}

}
