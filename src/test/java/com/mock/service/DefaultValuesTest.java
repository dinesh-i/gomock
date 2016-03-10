package com.mock.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mock.service.configuration.MockConfiguration;
import com.mock.service.value.generators.DefaultValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { MockConfiguration.class })
public class DefaultValuesTest {

	@Autowired
	private DefaultValues defaultValues;
	private Map<String, Object> defaultValuesMap;

	@Before
	public void setUp() throws Exception {
		defaultValuesMap = defaultValues.getDefaultValuesMap();
	}

	@Test
	public void testGetDefaultValuesMap() {
		assertAndPrint(Integer.class);
		assertAndPrint(BigDecimal.class);
	}

	public void assertAndPrint(Class<?> class1) {
		Object object = defaultValuesMap.get(class1.getSimpleName());
		assertNotNull(object);
	}

}
