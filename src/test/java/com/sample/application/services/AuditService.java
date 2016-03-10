package com.sample.application.services;

public interface AuditService {

	void addAuditRecord();

	int getTotalCountOfAuditRecords();

	int getTotalCountOfInactiveAuditRecords();

}