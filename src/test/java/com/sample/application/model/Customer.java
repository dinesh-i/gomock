package com.sample.application.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Customer {

	private CustomerContact customerContact;
	private Gender gender;
	private Timestamp createdTimeStamp;
	private int age;
}
