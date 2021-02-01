package com.governmentcio.seleniumframework.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.ClassRule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.governmentcio.seleniumframework.pom.factory.WebDriverFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Records the outcome of each test case using the {@link ExtentReports}
 * instance passed in the constructor. It overrides the
 * {@link #starting(Description)}, {@link #failed(Throwable, Description)},
 * {@link #succeeded(Description)} and {@link #finished(Description)} methods
 * and uses the {@link ExtentReports} from the {@link TestCaseRecorder} passed
 * into the constructor to record the outcome of a test case.
 * <p>
 * 
 * @author William Drew
 * @version 1.0
 * @since 2.0
 * @see TestWatcher
 * @see ExtentReports
 */
public class TestCaseRecorder extends TestWatcher {

	/**
	 * {@link WebDriver} instance that will be used to drive the testing.
	 */
	private WebDriver wd;

	/**
	 * {@link ExtentReports} for all the test cases in the unit test.
	 */
	private TestReporter tr;

	/**
	 * Instantiated at the start of the unit test and used to record the outcome
	 * of each test case.
	 */
	private ExtentReports er;

	/**
	 * Name and location of the report file.
	 */
	private String screenShotBaseDirectory =
			System.getProperty("user.dir") + "/target";

	/**
	 * Formatter for date used to form the screen shot file name.
	 */
	private static final DateFormat REPORT_DATE_FORMAT =
			new SimpleDateFormat("dd-MM-YYYY HH.mm.ss");

	/**
	 * Current instance of {@link ExtentTest} for the currently executing test
	 * case.
	 */
	private static ExtentTest currentTest;

	/**
	 * Takes a {@link TestReporter} instance which is a {@link ClassRule} which
	 * encapsulates {@link ExtentReports} for reporting the outcome for all test
	 * cases in this unit test.
	 * 
	 * @param testReporter
	 *          {@link TestReporter} instance which is a {@link ClassRule} which
	 *          encapsulates {@link ExtentReports}.
	 * @param webDriver
	 *          {@link WebDriver} used to drive the tests.
	 * @see WebDriverFactory
	 */
	public TestCaseRecorder(final TestReporter testReporter,
			final WebDriver webDriver) {

		if (null == testReporter) {
			throw new IllegalArgumentException("TestReporter argument is null");
		}
		tr = testReporter;

		if (null == webDriver) {
			throw new IllegalArgumentException("WebDriver argument is null");
		}
		this.wd = webDriver;

		if (null == tr.getExtentsReports()) {
			throw new IllegalArgumentException(
					"TestReporter returned null for ExtentReports instance.");
		}

		er = tr.getExtentsReports();
	}

	/**
	 * Called by the JUnit framework when a test case succeeds and records the
	 * method name from the {@link Description} parameter appended with the
	 * string "was successful" into the test case log.
	 */
	@Override
	protected final void succeeded(final Description description) {
		super.succeeded(description);
		// step log
		currentTest.log(LogStatus.PASS,
				description.getMethodName() + " was successful.");
	}	
	
		
	public final void success(final String description) {	
		// step log
		currentTest.log(LogStatus.PASS,
				description+ " was successful.");
	}
	
	
		
	public final void fail(final String description) {	
		// step log
		currentTest.log(LogStatus.FAIL,
				description);
		takeScreenshot();
		
	}


	/**
	 * Called by the JUnit framework when a test case fails. The reason for the
	 * test failure provided by the {@link Description} is recorded in the
	 * testing log. Then a screen shot is taken of the current page and written
	 * to the target/extents-report directory using the file name from
	 * {@link #getScreenShotFile(Description)}.
	 * 
	 */
	@Override
	protected final void failed(final Throwable e,
			final Description description) {
		super.failed(e, description);

		// step log
		currentTest.log(LogStatus.FAIL,
				description.getMethodName() + " failed. Reason: " + e.toString());

		TakesScreenshot takesScreenshot = (TakesScreenshot) wd;
		File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File destFile = getScreenShotFile(description);

		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException ioe) {
			currentTest.log(LogStatus.FAIL,
					"Unable to record screenshot to destination file ["
							+ destFile.getAbsolutePath() + "] - "
							+ ioe.getLocalizedMessage());
		}
		
	  InputStream is;
		try {
			is = new FileInputStream(destFile);
			 byte[] imageBytes = IOUtils.toByteArray(is);
			  String base64 = Base64.getEncoder().encodeToString(imageBytes);
			  currentTest.log(LogStatus.FAIL, "Snapshot below: "
	          + currentTest.addBase64ScreenShot("data:image/png;base64," + base64));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
    
    

	}
	
	public void takeScreenshot(){
		TakesScreenshot takesScreenshot = (TakesScreenshot) wd;

		File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(screenShotBaseDirectory+"/target/FailedTest.png");

		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException ioe) {
			currentTest.log(LogStatus.FAIL,
					"Unable to record screenshot to destination file ["
							+ destFile.getAbsolutePath() + "] - "
							+ ioe.getLocalizedMessage());
		}
		
	  InputStream is;
		try {
			is = new FileInputStream(destFile);
			 byte[] imageBytes = IOUtils.toByteArray(is);
			  String base64 = Base64.getEncoder().encodeToString(imageBytes);
			  currentTest.log(LogStatus.FAIL, "Snapshot below: "
	          + currentTest.addBase64ScreenShot("data:image/png;base64," + base64));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
	}

	/**
	 * Called by the JUnit framework before a test case starts and records the
	 * method name from the {@link Description} parameter appended with the
	 * string "started" into the test case log.
	 */
	@Override
	protected final void starting(final Description description) {
		super.starting(description);
		currentTest = er.startTest(description.getDisplayName());

		// Record the completion of the test in the log
		currentTest.log(LogStatus.INFO, description.getMethodName() + " started.");
	}

	/**
	 * Logs data to the current test log.
	 * 
	 * @param name
	 *          Method event occurred.
	 * @param data
	 *          Data to be logged.
	 */
	public final void logEvent(final TestName name, final String data) {
		// Record an event in the log
		currentTest.log(LogStatus.INFO,
				name.getMethodName() + " - [" + data + "]");
	}

	/**
	 * Called by the JUnit framework at the conclusion of a test case and records
	 * the method name from the {@link Description} parameter appended with the
	 * string "finished" into the test case log.
	 */
	@Override
	protected final void finished(final Description description) {
		super.finished(description);

		// Record the completion of the test in the log
		currentTest.log(LogStatus.INFO,
				description.getMethodName() + " finished.");

		er.endTest(currentTest);
		er.flush();
	}

  /**
   * Generates the file name to be used for a screen shot generated due to a
   * test case failure. The file name is a concatenation of
   * {@link #screenShotBaseDirectory}, {@link Description#getDisplayName()},
   * {@link Description#getMethodName()} and {@link #REPORT_DATE_FORMAT}
   * appended with ".png". The {@link File} created is used to stored the screen
   * shot.
   * 
   * @param description
   *          {@link Description} for the current test case.
   * @return File to store a screen shot.
   */
  private File getScreenShotFile(final Description description) {
    StringBuilder fileName = new StringBuilder();
    fileName.append(screenShotBaseDirectory);
    fileName.append("/");
    fileName.append("target");
    fileName.append("/");
    fileName.append(description.getMethodName());
    fileName.append("-");
    fileName.append(REPORT_DATE_FORMAT.format(new Date()));
    fileName.append(".png");
    return new File(fileName.toString());
    
  }

	public static ExtentTest getCurrentTest() {
		return currentTest;
	}
}
