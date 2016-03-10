package com.sample.application.services;

import com.sample.application.model.Customer;

public interface IntegrationService {

	Customer getCustomerInformation(String customerId);

	void refreshCustomerData();

}