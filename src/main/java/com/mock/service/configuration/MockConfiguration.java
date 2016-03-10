package com.mock.service.configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mock.service.model.MockSet;

@Configuration
@ComponentScan(basePackages = "com.mock")
@EnableAspectJAutoProxy
public class MockConfiguration {

	private final String MOCKED_SERVICES_DEFAULT_LOCATION = "/properties/mockedServices.properties";

	private String mockedServicesLocation = MOCKED_SERVICES_DEFAULT_LOCATION;

	private static Logger LOGGER = LoggerFactory.getLogger(MockConfiguration.class);

	@Autowired
	private MockInitializer mockInitializer;

	public MockConfiguration() {
		super();
	}

	@PostConstruct
	public void initialize() {
		LOGGER.debug("initialize - starting");
		if ((null != mockInitializer)) {
			LOGGER.debug("initialized - starting");
			if ((null != mockInitializer.getMockedServicesLocation()))
				this.mockedServicesLocation = mockInitializer.getMockedServicesLocation();
		}
	}

	@Bean
	public MockSet loadMockSet() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedReader br = getMockedServicesBufferedReader();
			MockSet mockSet = gson.fromJson(br, MockSet.class);

			if ((null != mockInitializer) && (null != mockInitializer.getDefaultFieldMap()))
				mockSet.getDefaultFieldMap().putAll(mockInitializer.getDefaultFieldMap());

			LOGGER.debug("loaded MockSet with initial values : {}", gson.toJson(mockSet));

			Map<String, Object> defaultTypeMap = mockSet.getDefaultTypeMap();
			Map<String, Object> validatedTypeMap = getValidatedTypeMap(defaultTypeMap);
			mockSet.setDefaultTypeMap(validatedTypeMap);

			LOGGER.debug("MockSet after removing defaultTypeMaps that can't be mapped to corresponding types : {}", gson.toJson(mockSet));
			return mockSet;
		} catch (Exception e) {
			LOGGER.error("Exception[{}] in loading the mock set. Initializing to empty mockset", e.getMessage());
		}
		return new MockSet();
	}

	private Map<String, Object> getValidatedTypeMap(Map<String, Object> defaultTypeMap) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if ((null == defaultTypeMap) || (defaultTypeMap.entrySet().size() == 0))
			return new HashMap<>();

		Map<String, Object> validatedTypeMap = new HashMap<>();
		for (Entry<String, Object> entry : defaultTypeMap.entrySet()) {
			if (null != entry) {
				try {
					String typeName = entry.getKey();
					Object typeObject = entry.getValue();
					String typeObjectString = (StringUtils.isEmpty(typeObject)) ? "" : typeObject.toString();
					Class<?> className = Class.forName(typeName);
					validatedTypeMap.put(typeName, gson.fromJson(typeObjectString, className));
				} catch (Exception e) {
					LOGGER.error("Exception[{}] occurred in validating and setting the type[{}]. Skipping this field.", e.getMessage(),
							entry.getKey());
					continue;
				}
			}
		}
		return validatedTypeMap;
	}

	private BufferedReader getMockedServicesBufferedReader() {
		InputStream resourceAsStream = this.getClass().getResourceAsStream(getMockedServicesLocation());
		return new BufferedReader(new InputStreamReader(resourceAsStream));
	}

	public String getMockedServicesLocation() {
		return mockedServicesLocation;
	}

}
