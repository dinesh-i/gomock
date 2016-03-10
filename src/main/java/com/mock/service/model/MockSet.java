package com.mock.service.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockSet {
	private Set<MockInvocations> mockInvocations = new HashSet<>();
	private Map<String, Object> defaultFieldMap = new HashMap<>();
	private Map<String, Object> defaultTypeMap = new HashMap<>();
}
