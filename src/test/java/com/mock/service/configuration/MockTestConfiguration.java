package com.mock.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = { "com.mock", "com.sample" })
@ImportResource("classpath:spring/application-config.xml")
public class MockTestConfiguration {
	@Bean
	public MockInitializer loadMockInitializer() {
		return new MockInitializer();
	}
}
