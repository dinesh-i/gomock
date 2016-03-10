package com.mock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mock.service.configuration.MockTestConfiguration;
import com.sample.application.model.Customer;
import com.sample.application.model.CustomerContact;
import com.sample.application.services.AuditService;
import com.sample.application.services.IntegrationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { MockTestConfiguration.class })
public class MockResponseProcessorTest {

	@Autowired
	IntegrationService integrationService;

	@Autowired
	AuditService auditService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void serviceInvocation_Should_Convert_Primitive() throws Exception {
		int totalCountOfInactiveAuditRecords = auditService.getTotalCountOfInactiveAuditRecords();
		assertThat(totalCountOfInactiveAuditRecords, is(equalTo(0)));

	}

	// @Ignore
	@Test
	public void serviceInvocation_ShouldCallMockResponseProcessorAspect() {
		Customer customer = integrationService.getCustomerInformation("");

		CustomerContact customerContact = customer.getCustomerContact();
		assertThat(customerContact.getCustomerName(), is(equalTo("customerName")));

	}

	// @Ignore
	@Test
	public void serviceInvocation_ShouldSkipMockResponseProcessing_ForMethodsToBeMockedThatReturnVoid() {
		integrationService.refreshCustomerData();
		// TODO: Add verification that actual method is not called
	}

	// TODO: Implement the logic to consider method overriding in the methods to
	// be mocked
	@Ignore
	@Test
	public void shouldConsiderMethodOverriding() throws Exception {

	}
}
