/**
 * 
 */
package com.governmentcio.seleniumframework.pom;

import static org.junit.Assert.assertNotNull;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;
import com.governmentcio.seleniumframework.rules.TestCaseRecorder;
import com.governmentcio.seleniumframework.rules.TestReporter;
import com.governmentcio.seleniumframework.rules.WebDriverResource;

/**
 * Tests using Selenium home page and navigation to the Wiki page.
 * <p>
 * 
 * @author William Drew (wdrew@governmentcio.com)
 * @version 1.0
 * @since 1.0
 * @see PageObject
 * @see AbstractPageObject
 */
public class SeleniumHomePageObjectTest {

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
			"Selenium Home Page Navigation", "Selenium home page navigation");

	/**
	 * Name of each test method provided by the {@link TestName} rule passed into
	 * the constructor.
	 */
	@Rule
	public TestName name = new TestName();

	/**
	 * {@link TestCaseRecorder} for this unit test which records the progress of
	 * each test case and the unit test overall.
	 */
	@Rule
	public TestCaseRecorder tr =
			new TestCaseRecorder(testReporter, webDriverResource.getDriver());

	/**
	 * Test method accesses the Selenium home page, clicks the download button
	 * and then proceeds to the Wiki page.
	 * 
	 * @throws InterruptedException
	 *           Thrown if this thread is unexpectedly interrupted before
	 *           returning from the default wait period.
	 */
	//@Test
	public final void testSeleniumHomePageObject() throws InterruptedException {

		SeleniumHomePageObject seleniumHomePageObj =
				new SeleniumHomePageObject(webDriverResource.getDriver());

		assertNotNull(seleniumHomePageObj);

		seleniumHomePageObj.goHome();

		SeleniumDownloadPageObject seleiumDownLoadPage =
				seleniumHomePageObj.clickDownloadLink();

		assertNotNull(seleiumDownLoadPage);

		seleiumDownLoadPage.gotoWikiPage();
	}
}
