package com.governmentcio.seleniumframework.pom;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.otp.RoundCubeOtpRetrieval;
import com.governmentcio.seleniumframework.pom.AbstractPageObject;
import com.governmentcio.seleniumframework.pom.PageObject;
import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;
import com.governmentcio.seleniumframework.rules.TestCaseRecorder;
import com.governmentcio.seleniumframework.rules.TestReporter;
import com.governmentcio.seleniumframework.rules.WebDriverResource;

/**
 * Performs some simple navigation of the Selenium home page.
 * <p>
 * 
 * @author William Drew (wdrew@governmentcio.com)
 * @version 1.0
 * @since 1.0
 * @see PageObject
 * @see AbstractPageObject
 */
public class SeleniumDownloadPageObjectTest {

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
      "Selenium Download", "Testing of Selenium download functionality");

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
   * 
   * @throws InterruptedException
   *           Thrown if this thread is unexpectedly interrupted before
   *           returning from a wait period.
   * @throws IOException 
   */
  @Test
  public final void testSeleniumDownloadPageObject()
      throws InterruptedException {

    SeleniumDownloadPageObject po =
        new SeleniumDownloadPageObject(webDriverResource.getDriver());

    po.goHome();

  }

  /**
   * 
   * @throws InterruptedException
   *           Thrown if this thread is unexpectedly interrupted before
   *           returning from a wait period.
   */
 // @Test
  public final void testGotoWikiPage() throws InterruptedException {

    SeleniumDownloadPageObject po =
        new SeleniumDownloadPageObject(webDriverResource.getDriver());

    po.goHome();

    po.gotoWikiPage();

  }

}
