package com.mock.service.configuration;

import java.util.Map;

//@Component
public class MockInitializer {

	private String mockedServicesLocation;
	private Map<String, Object> defaultFieldMap;

	public MockInitializer() {
		super();
	}

	public MockInitializer(String mockedServicesLocation, Map<String, Object> defaultFieldMap) {
		super();
		this.mockedServicesLocation = mockedServicesLocation;
		this.defaultFieldMap = defaultFieldMap;
	}

	public Map<String, Object> getDefaultFieldMap() {
		return defaultFieldMap;
	}

	public void setDefaultFieldMap(Map<String, Object> defaultFieldMap) {
		this.defaultFieldMap = defaultFieldMap;
	}

	public String getMockedServicesLocation() {
		return mockedServicesLocation;
	}

	public void setMockedServicesLocation(String mockedServicesLocation) {
		this.mockedServicesLocation = mockedServicesLocation;
	}

}
