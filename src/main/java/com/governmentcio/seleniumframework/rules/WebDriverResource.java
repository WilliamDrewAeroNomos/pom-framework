/**
 * 
 */
package com.governmentcio.seleniumframework.rules;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.configuration.TestConfigurationData;
import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;
import com.governmentcio.seleniumframework.zap.ZAP;

/**
 * WebDriverResource subclasses ExternalResource and overrides the
 * {@link #before()} and {@link #after()} methods in order to manage the
 * lifecycle of a WebDriver instance. In the before() method
 * WebDriverFactory.getInstance().getDriver() is called to instantiate the
 * webDriver instance which will be available to all test cases throughout the
 * life of the unit test. At the end of the unit test,
 * {@link WebDriverFactory#closeAndQuit(WebDriver)} is called the framework to
 * clean up the WebDriver instance.
 * 
 * @author William Drew
 * @version 1.0
 * @since 2.0
 * @see ExternalResource
 * @see WebDriver
 * @see WebDriverFactory
 */
public class WebDriverResource extends ExternalResource {

  /**
   * Instance of {@link WebDriver} returned by {@link WebDriverFactory}.
   */
  private WebDriver webDriver;

  /**
   * 
   */
  public WebDriverResource() {

  }

  /**
   * Called by the JUnit framework before the start of a unit test. Instantiates
   * the WebDriver field {@code webDriver} with a call to
   * WebDriverFactory.getInstance().getDriver().
   * 
   * @see WebDriver
   * @see WebDriverFactory
   */
  @Override
  protected final void before() throws Throwable {
    super.before();
    webDriver = WebDriverFactory.getInstance().getDriver();
  }

  /**
   * Called by the JUnit framework at the end of the JUnit test and closes and
   * quits the WebDriver instantiated in the {@link #before()} method.
   * 
   * @see WebDriver
   * @see WebDriverFactory
   */
  @Override
  protected final void after() {
    super.after();
    WebDriverFactory.closeAndQuit(webDriver);
    if("true".equals(System.getProperty("enablezap"))) {
    	ZAP.getReport();
    }
  }

  /**
   * Returns the instantiation of the WebDriver used during the duration of the
   * unit test. This single instance of TestRecorder is passed to the
   * {@link TestCaseRecorder#TestCaseRecorder(TestReporter, WebDriver)}
   * constructor. It's expected the WebDriver will be used by the
   * {@link TestCaseRecorder} to record the outcome of each test case.
   * 
   * @see TestReporter
   * @see TestCaseRecorder
   * @see WebDriver
   * 
   * @return {@link WebDriver} instantiated by the {@link WebDriverFactory}.
   */
  public final WebDriver getDriver() {
    return webDriver;
  }
}
