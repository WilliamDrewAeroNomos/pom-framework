package com.governmentcio.seleniumproto;

import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;
import com.governmentcio.seleniumframework.pom.PageObject;
import com.governmentcio.seleniumframework.pom.SeleniumDownloadPageObject;
import com.governmentcio.seleniumframework.pom.SeleniumHomePageObject;
import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;

import junit.framework.TestCase;

/**
 * Tests using Google search functionality.
 * <p>
 * 
 * @author William Drew (wdrew@governmentcio.com)
 * @version 1.0
 * @since 1.0
 * @see PageObject
 * @see AbstractPageObject
 */
public class ITGoogleSearchPageObjectTest extends TestCase {

	/**
	 * Instance of {@link WebDriver} returned by {@link WebDriverFactory}.
	 */
	private WebDriver webDriver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public final void setUp() throws Exception {
		super.setUp();

		webDriver = WebDriverFactory.getInstance().getDriver();

	}

	/*
	 * Checks for a valid reference to the WebDriver and closes and quits if
	 * valid.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();

		WebDriverFactory.closeAndQuit(webDriver);
	}

	/**
	 * Tests a basic search of the string "Selenium" via the Google web site
	 * Navigates through the Selenium web site including the download and Wiki
	 * pages.
	 * 
	 * @throws InterruptedException
	 *           Thrown if this thread is unexpectedly interrupted before
	 *           returning from a wait period.
	 */
	public final void testGooglePageBasicSearch() throws InterruptedException {

		GoogleHomePageObject gpo = new GoogleHomePageObject(webDriver);

		assertNotNull(gpo);

		gpo.goHome();

		// Search using the text "Selenium"
		GoogleSearchResultsSeleniumPageObject spo = gpo.search("Selenium");

		assertNotNull(spo);

		SeleniumHomePageObject seliumHomePageObj = spo.getSeleniumHomePageLink();

		assertNotNull(seliumHomePageObj);

		SeleniumDownloadPageObject seleiumDownLoadPage =
				seliumHomePageObj.clickDownloadLink();

		assertNotNull(seleiumDownLoadPage);

		seleiumDownLoadPage.gotoWikiPage();

		webDriver.navigate().back();
	}

}
