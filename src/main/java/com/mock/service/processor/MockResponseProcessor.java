package com.mock.service.processor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mock.service.MockValueGenerator;
import com.mock.service.util.ProceedingJoinPointUtil;

@Component
public class MockResponseProcessor {

	@Autowired
	private MockValueGenerator mockValueGenerator;

	private static Logger LOGGER = LoggerFactory.getLogger(MockResponseProcessor.class);

	public Object getMockResponse(ProceedingJoinPoint proceedingJoinPoint) {
		Class<?> returnType = ProceedingJoinPointUtil.getMethodReturnType(proceedingJoinPoint);
		if (returnType == void.class)
			return null;

		try {
			return mockValueGenerator.getMockValue(returnType);
		} catch (NoSuchMethodException e) {
			LOGGER.error("Exception[{}] occurred while populating the mock object", e.getMessage());
		}

		return null;
	}
}