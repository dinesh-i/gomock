package com.mock.service.processor;

import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mock.service.model.MockInvocations;
import com.mock.service.model.MockSet;
import com.mock.service.util.ProceedingJoinPointUtil;

@Component
public class MockRouter {

	@Autowired
	private MockResponseProcessor mockResponseProcessor;

	@Autowired
	private MockSet mockSet;

	private static Logger LOGGER = LoggerFactory.getLogger(MockRouter.class);

	Predicate<ProceedingJoinPoint> mockRouterPredicate = new Predicate<ProceedingJoinPoint>() {

		@Override
		public boolean test(ProceedingJoinPoint proceedingJoinPoint) {
			Set<MockInvocations> mockInvocationsSet = mockSet.getMockInvocations();
			for (MockInvocations mockInvocations : mockInvocationsSet) {
				String classNameToBeMocked = mockInvocations.getClassName();
				if (StringUtils.equals(classNameToBeMocked, ProceedingJoinPointUtil.getAbsoluteClassName(proceedingJoinPoint))) {
					Set<String> methodNamesToBeMocked = mockInvocations.getMethodNames();
					if (!CollectionUtils.isEmpty(methodNamesToBeMocked)
							&& methodNamesToBeMocked.contains(ProceedingJoinPointUtil.getMethodName(proceedingJoinPoint))) {
						return true;
					}
				}
			}

			return false;
		}
	};

	public Object route(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		if (mockRouterPredicate.test(proceedingJoinPoint)) {
			LOGGER.debug("Processing mock response for the method [{}]", ProceedingJoinPointUtil.getMethodName(proceedingJoinPoint));
			return mockResponseProcessor.getMockResponse(proceedingJoinPoint);
		} else {
			if (ProceedingJoinPointUtil.getMethodReturnType(proceedingJoinPoint) == void.class) {
				proceedingJoinPoint.proceed();
				return null;
			} else {
				return proceedingJoinPoint.proceed();
			}
		}
	}

}