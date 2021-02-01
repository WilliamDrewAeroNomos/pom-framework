package com.governmentcio.seleniumframework.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.governmentcio.seleniumframework.pom.PageObject;

/**
 * RepeatableRule provides the ability to execute a single test case repeatably
 * for a number of iterations designated by the Repeat annotation.
 * <p>
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see PageObject
 */
public class RepeatRule implements TestRule {

	private static class RepeatStatement extends Statement {

		/**
		 * document me!
		 */
		private final int times;

		/**
		 * document me!
		 */
		private final Statement statement;

		/**
		 * 
		 * @param times
		 *          Number of times to run the test.
		 * @param statement
		 *          to be determined
		 */
		private RepeatStatement(final int times, final Statement statement) {
			this.times = times;
			this.statement = statement;
		}

		@Override
		public void evaluate() throws Throwable {
			for (int i = 0; i < times; i++) {
				statement.evaluate();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public Statement apply(final Statement statement,
			final Description description) {
		Statement result = statement;
		Repeat repeat = description.getAnnotation(Repeat.class);
		if (repeat != null) {
			int times = repeat.times();
			result = new RepeatStatement(times, statement);
		}
		return result;
	}
}