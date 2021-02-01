package com.governmentcio.seleniumframework.rules;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.rules.ExternalResource;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * Encapsulates the functionality for creating the overall report for all the
 * test cases within a unit test. The class establishes the target reporting
 * directory and sets the report name and headline. TestReporter subclasses the
 * {@link ExternalResource} and is expected to be designated as a ClassRule. The
 * class overrides the {@code before} method which instantiates the
 * {@link ExtentReports} attribute {@code er} using the {@code rptName} and
 * {@code rptHLine} passed into the constructor to further configure the report.
 * 
 * @author William Drew
 * @version 1.0
 * @since 2.0
 * @see ExternalResource
 */
public class TestReporter extends ExternalResource {
  /**
   * {@link ExtentReports} for all the test cases in the unit test.
   */
  private ExtentReports er;

  /**
   * Format of the date used to create the report file name.
   */
  private static final DateFormat REPORT_DATE_FORMAT =
      new SimpleDateFormat("dd-MM-YYYY HH.mm.ss");

  /**
   * Name and location of the report file.
   */
  private String reportFileBaseDirectory =
      System.getProperty("user.dir") + "/target/extent-reports/";

  /**
   * Name of the report.
   */
  private final String rptName;

  /**
   * Headline of the report.
   */
  private final String rptHLine;

  /**
   * Initialized with the report name and report headline. The
   * <code>reportFileBaseDirectory</code> is used a the target directory which
   * will default to extent-reports under target directory.
   * 
   * @param reportName
   *          Name of the test report.
   * @param reportHeadline
   *          Primary headline of the report.
   * @throws IllegalArgumentException
   *           Thrown if either {@code reportName} or {@code reportHeadLine} are
   *           null.
   * 
   */
  public TestReporter(final String reportName, final String reportHeadline) {

    File reportDir = new File(reportFileBaseDirectory);
    if (!reportDir.exists()) {
      reportDir.mkdir();
    }

    this.rptName = reportName;
    this.rptHLine = reportHeadline;
  }

  /**
   * Called at the start of the unit test, it instantiates the
   * {@link ExtentReports} using the directory location from
   * {@link TestReporter#getReportFileName()} and sets the report name and
   * header from values passed into
   * {@link TestReporter#TestReporter(String, String)}.
   * 
   */
  @Override
  protected final void before() throws Throwable {
    super.before();
    er = new ExtentReports(getReportFileName(), true);
    er.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
  }

  /**
   * Returns the reference to the {@link ExtentReports} used to record the test
   * case progress. This is expected to be used by the {@link TestCaseRecorder}
   * to record the outcome of each test case.
   * 
   * @return {@link ExtentReports} reference to record the outcome of each unit
   *         test.
   */
  public final ExtentReports getExtentsReports() {
    return er;
  }

  /**
   * Forms the name of the report file by concatenating the following :
   * 
   * <ul>
   * <li>{@link TestReporter#reportFileBaseDirectory} +
   * <li>"/" +
   * <li>report name passed into the constructor +
   * <li>"-" +
   * <li>current timestamp in the format of
   * {@link TestReporter#REPORT_DATE_FORMAT} (dd-MM-YYYY HH.mm.ss) +
   * <li>".html"
   * </ul>
   * 
   * All test reports will be located under the "target" directory under the
   * standard project directory structure (i.e. - /target/extent-reports/GSA IAE
   * FBO Test-06-08-2016 09.04.39.html).
   * <p>
   * 
   * @return Full formed path of the test report file name.
   */
  
  private String getReportFileName() {
  	StringBuilder fileName = new StringBuilder();
    fileName.append(reportFileBaseDirectory);
    fileName.append("/");
    fileName.append(rptName);
    fileName.append("-");
    fileName.append(REPORT_DATE_FORMAT.format(new Date()));
    fileName.append(".html");
    return fileName.toString();
  }
}
