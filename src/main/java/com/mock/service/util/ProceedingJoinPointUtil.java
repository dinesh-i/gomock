package com.mock.service.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class ProceedingJoinPointUtil {
	public static String getAbsoluteClassName(ProceedingJoinPoint proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		return methodSignature.getDeclaringTypeName();
	}

	public static String getMethodName(ProceedingJoinPoint proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		return methodSignature.getName();
	}

	public static Class<?> getMethodReturnType(ProceedingJoinPoint proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		return methodSignature.getReturnType();
	}
}