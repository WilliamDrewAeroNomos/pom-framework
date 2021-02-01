package com.governmentcio.seleniumframework.pom;

import org.junit.Rule;

import com.governmentcio.seleniumframework.rules.Repeat;
import com.governmentcio.seleniumframework.rules.RepeatRule;

public class RandomRangeValueCalculatorImplTest {

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	public static int increment = 1;

	// @Test
	@Repeat(times = 10)
	public void testCalculateRangeValue() {
		System.out.print(increment++);
	}
}