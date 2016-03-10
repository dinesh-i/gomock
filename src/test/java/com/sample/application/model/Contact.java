package com.sample.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	private String streetAddress;
	private String city;
    private String state;
	private String zip;
	private String emailAddress;
	private String countryCode;
	private String phoneNumber;
	
}
