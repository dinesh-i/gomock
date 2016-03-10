package com.mock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mock.service.configuration.MockConfiguration;
import com.mock.service.model.MockInvocations;
import com.mock.service.model.MockSet;
import com.sample.application.model.Customer;
import com.sample.application.model.Gender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { MockConfiguration.class })
public class MockPopulatorTest {

	private static Logger LOGGER = LoggerFactory.getLogger(MockPopulatorTest.class);

	@Autowired
	MockPopulator mockPopulator;

	private Gson gson;

	private Customer customer;

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().setPrettyPrinting().create();
		customer = new Customer();
	}

	@Test
	public void shouldConvertString_ChildObjects_Primitives_Arrays_Enums() {
		mockPopulator.populateMockObject(customer);
		LOGGER.debug(customer.toString());
		LOGGER.debug(gson.toJson(customer));
		assertTrue(customer.getCustomerContact().getCustomerName().equals("customerName"));
		assertTrue(customer.getCustomerContact().getRelationshipId() == BigDecimal.ONE);
		assertThat(customer.getAge(), is(equalTo(0)));
		assertThat(customer.getGender().getClass(), is(equalTo(Gender.class)));
		assertThat(customer.getCustomerContact().getNames().length, is(equalTo(1)));
		assertThat(customer.getCustomerContact().getEmailIds().size(), is(equalTo(1)));
	}

	@Ignore
	@Test
	public void shouldConvertFromJavaObjectToJson() throws Exception {
		MockSet mockSet = new MockSet();
		Set<MockInvocations> mockInvocations = new HashSet<MockInvocations>();
		mockInvocations.add(new MockInvocations("className1", new HashSet<String>(Arrays.asList("method1", "method2"))));
		mockInvocations.add(new MockInvocations("className2", new HashSet<String>(Arrays.asList("method1", "method2"))));
		mockSet.setMockInvocations(mockInvocations);

		System.out.println(gson.toJson(mockSet));
	}

	@Ignore
	@Test
	public void shouldReadFromJsonAndCovertToJavaObject() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/properties/mockedServices.properties"));

		// convert the json string back to object
		MockSet obj = gson.fromJson(br, MockSet.class);

		System.out.println(obj);
	}

	@Ignore
	@Test
	public void testName() throws Exception {
		List<String> emailIds = new ArrayList<>();
		System.out.println(emailIds.getClass().isAssignableFrom(Collection.class));
		System.out.println(Collection.class.isAssignableFrom(emailIds.getClass()));
	}
}
