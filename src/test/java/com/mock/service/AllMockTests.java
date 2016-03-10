package com.mock.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DefaultValuesTest.class, MockPopulatorTest.class, MockResponseProcessorTest.class })
public class AllMockTests {

}
