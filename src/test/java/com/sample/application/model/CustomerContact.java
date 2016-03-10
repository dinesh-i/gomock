package com.sample.application.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContact {

	private String customerName;
	private Contact contact;
	private String[] names;
	private Integer contactId;
	private List<String> emailIds;
	private BigDecimal relationshipId;
}
