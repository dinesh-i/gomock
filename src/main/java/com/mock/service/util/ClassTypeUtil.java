package com.mock.service.util;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class ClassTypeUtil {

	public static boolean isWrapper(Class<?> type) {
		List<Object> wrapperClasses = Arrays.asList(Boolean.FALSE, new Character('A'), new Byte((byte) 0), new Short((short) 0), new Integer(1),
				new Long(1), new Float(1f), new Double(1d));

		return wrapperClasses.parallelStream().anyMatch(wrapperClass -> type.isInstance(wrapperClass));
	}

	public static boolean isString(Class<?> type) {
		return type.isInstance("");
	}

	public static boolean isTimestamp(Class<?> type) {
		return type.isInstance(new Timestamp(2131231));
	}

}
