package com.sample.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sample.application.model.Customer;

@Component
public class IntegrationServiceImpl implements IntegrationService {

	private static Logger LOGGER = LoggerFactory.getLogger(IntegrationServiceImpl.class);

	@Override
	public Customer getCustomerInformation(String customerId) {
		return null;
	}

	@Override
	public void refreshCustomerData() {
		LOGGER.debug("Refreshed customer data");
		return;
	}

}
