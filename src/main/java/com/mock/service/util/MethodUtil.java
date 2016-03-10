package com.mock.service.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodUtil {

	public static Type getFirstGenericParameterType(Method method) {
		return method.getGenericParameterTypes()[0];
	}

	public static Class<?> getFirstParameterType(Method method) {
		return method.getParameterTypes()[0];
	}

}