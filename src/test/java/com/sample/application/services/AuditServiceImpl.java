package com.sample.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditServiceImpl implements AuditService {

	private static Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

	@Override
	public void addAuditRecord() {
		LOGGER.debug("Added the audit record");
		return;
	}

	@Override
	public int getTotalCountOfAuditRecords() {
		LOGGER.debug("Getting the total count of audit records");
		return 100;
	}

	@Override
	public int getTotalCountOfInactiveAuditRecords() {
		LOGGER.debug("Getting the total count of audit records");
		return 10;
	}
}
