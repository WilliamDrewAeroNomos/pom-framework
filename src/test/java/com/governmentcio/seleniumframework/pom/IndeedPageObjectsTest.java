/**
 * 
 */
package com.governmentcio.seleniumframework.pom;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;
import com.governmentcio.seleniumframework.rules.TestCaseRecorder;
import com.governmentcio.seleniumframework.rules.TestReporter;
import com.governmentcio.seleniumframework.rules.Verifier;
import com.governmentcio.seleniumframework.rules.WebDriverResource;

/**
 * Performs some simple navigation of the Indeed home page.
 * <p>
 * 
 * @author William Drew (wdrew@governmentcio.com)
 * @version 1.0
 * @since 1.0
 * @see PageObject
 * @see AbstractPageObject
 */
public class IndeedPageObjectsTest {

	/**
	 * Instantiates, configures and cleanups the {@link WebDriver} using the
	 * {@link WebDriverFactory}.
	 * 
	 * @see WebDriver
	 * @see WebDriverFactory
	 */
	@ClassRule
	public static WebDriverResource webDriverResource = new WebDriverResource();

	/**
	 * Reports on the outcome of the overall test recording all test cases as
	 * captured in the {@link TestCaseRecorder}.
	 * 
	 * @see TestCaseRecorder
	 */
	@ClassRule
	public static TestReporter testReporter = new TestReporter(
			"Indeed Page Search", "Testing simple search on the Indeed site");

	/**
	 * Name of each test method provided by the {@link TestName} rule passed into
	 * the constructor.
	 */
	@Rule
	public TestName name = new TestName();

	private Verifier verifier;

	/**
	 * {@link TestCaseRecorder} for this unit test which records the progress of
	 * each test case and the unit test overall.
	 */
	@Rule
	public TestCaseRecorder tr =
			new TestCaseRecorder(testReporter, webDriverResource.getDriver());

	/**
	 * Tests simply loading the Indeed home page and executing a search for
	 * Selenium jobs in London.
	 * 
	 * @throws InterruptedException
	 *                                Thrown if this thread is unexpectedly
	 *                                interrupted before returning from a wait
	 *                                period.
	 */

	// @Test
	public final void testIndeedSeleniumJobsInLondon()
			throws InterruptedException {
		verifier = new Verifier(tr);

		webDriverResource.getDriver().navigate().to("http://www.indeed.co.uk");

		IndeedHomePageObject po =
				new IndeedHomePageObject(webDriverResource.getDriver());

		verifier.assertNotNull(po, "Object should not null");

		po.enterJobEntry("Selenium");

		po.enterJobLocation("London");

		po.search();

	}

	@After
	public void tearDown() {
		verifier.assertAll();
	}

}
