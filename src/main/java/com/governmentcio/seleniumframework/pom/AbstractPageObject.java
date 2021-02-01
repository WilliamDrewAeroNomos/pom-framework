package com.governmentcio.seleniumframework.pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * AbstractPageObject is the abstract base class for all concrete
 * {@link PageObject} implementations. It provides common functionality and
 * default values such as page load, script timeouts, etc. It also maintains
 * the instance of {@link WebDriver} used to instantiate the concrete
 * PageObject implementations.
 * <p>
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see PageObject
 */
public abstract class AbstractPageObject implements PageObject {

	/**
	 * Default value for time in {@link TimeUnit#SECONDS} to wait until a
	 * {@link WebElement} is found before throwing a timeout exception.
	 */
	private static final long IMPLICIT_WAIT_SECS_DEFAULT = 30L;
	/**
	 * Default time in {@link TimeUnit#SECONDS} to wait for a page load to
	 * complete before throwing a timeout exception.
	 */
	private static final long PAGE_LOAD_WAIT_SECS_DEFAULT = 600L;

	/**
	 * Default time in {@link TimeUnit#SECONDS} to wait for an asynchronous
	 * script to finish execution before throwing a timeout exception.
	 */
	private static final long SCRIPT_TIMEOUT_SECS_DEFAULT = 30L;

	/**
	 * Time in {@link TimeUnit#MILLISECONDS} to wait between entry of characters
	 * into an entry control.
	 */
	private static final int DEFAULT_WAIT_TIME_BETWEEN_ENTRIES_MSECS = 125;

	/**
	 * Time in {@link TimeUnit#SECONDS} to wait until a {@link WebElement} is
	 * found before throwing a timeout exception whose default value is
	 * IMPLICIT_WAIT_SECS_DEFAULT.
	 */
	private long implicitWait = IMPLICIT_WAIT_SECS_DEFAULT;

	/**
	 * Time in {@link TimeUnit#SECONDS} to wait for a page load to complete
	 * before throwing a timeout exception whose default value is
	 * PAGE_LOAD_WAIT_SECS_DEFAULT.
	 */
	private long pageLoadTimeout = PAGE_LOAD_WAIT_SECS_DEFAULT;

	/**
	 * Time in {@link TimeUnit#SECONDS} to wait for an asynchronous script to
	 * finish execution before throwing a timeout exception whose default value
	 * is SCRIPT_TIMEOUT_SECS_DEFAULT.
	 */
	private long scriptTimeout = SCRIPT_TIMEOUT_SECS_DEFAULT;

	/**
	 * Instance of the {@link WebDriver} used to initialize this instance which
	 * is passed by the implementing sub-class.
	 */
	private WebDriver driver;

	/**
	 * Called by the concrete implementing sub-class which passes a
	 * {@link WebDriver} instance. Sets the timeouts for the supplied WebDriver
	 * using the default values.
	 * {@link PageFactory#initElements(WebDriver, Object)} is called to
	 * initialize any {@link WebElement} that is defined in the sub-class.
	 * <p>
	 * 
	 * @param driverInstance
	 *          {@link WebDriver} supplied by the sub-class constructor.
	 */
	public AbstractPageObject(final WebDriver driverInstance) {

		if (null == driverInstance) {
			throw new IllegalArgumentException("WebDriver parameter was null");
		}

		this.driver = driverInstance;

		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout,
				TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(scriptTimeout,
				TimeUnit.SECONDS);

		PageFactory.initElements(driver, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#goHome()
	 */
	@Override
	public final PageObject goHome() {
		getDriver().get(getHomeURL());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public String getHomeURL() {
		return getDomain() + getPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getDomain()
	 */
	@Override
	public final String getDomain() {
		return System.getenv("COM.GOVERNMENTCIO.POM.DOMAIN");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getDriver()
	 */
	@Override
	public final WebDriver getDriver() {
		return driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#getPageloadTimeoutSecs
	 * ()
	 */
	@Override
	public final long getPageloadTimeoutSecs() {
		return pageLoadTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#setImplicitWaitSecs(
	 * long)
	 */
	@Override
	public final PageObject setImplicitWaitSecs(final long timeSecs) {
		this.implicitWait = timeSecs;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#
	 * setPageloadTimeoutSecs( long)
	 */
	@Override
	public final PageObject setPageloadTimeoutSecs(final long timeSecs) {
		this.pageLoadTimeout = timeSecs;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#setScriptTimeoutSecs(
	 * long)
	 */
	@Override
	public final PageObject setScriptTimeoutSecs(final long timeSecs) {
		this.scriptTimeout = timeSecs;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#pageWait(long)
	 */
	@Override
	public final void pageWait(final long timeInMillisecs) {

		try {
			Thread.sleep(timeInMillisecs);
		} catch (InterruptedException e) {
			// Restore the interrupted status to preserve the state.
			Thread.currentThread().interrupt();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#waitUntil(org.openqa.
	 * selenium.WebElement, int)
	 */
	@Override
	public final WebElement waitUntil(final WebElement webElement,
			final int numberOfSecs) {
		return new WebDriverWait(getDriver(), numberOfSecs)
				.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#getImplicitTimeoutSecs
	 * ()
	 */
	@Override
	public final long getImplicitTimeoutSecs() {
		return implicitWait;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#
	 * getScriptExecutionTimeoutSecs()
	 */
	@Override
	public final long getScriptExecutionTimeoutSecs() {
		return scriptTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#
	 * getWaitTimeBetweenEntriesMSecs()
	 */
	@Override
	public long getWaitTimeBetweenEntriesMSecs() {
		return DEFAULT_WAIT_TIME_BETWEEN_ENTRIES_MSECS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#typeInField(java.lang.
	 * String, org.openqa.selenium.WebElement)
	 */
	@Override
	public final void typeInField(final String input, final WebElement element) {
		typeInField(input, element, getWaitTimeBetweenEntriesMSecs());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#typeInField(java.lang.
	 * String, org.openqa.selenium.WebElement, long)
	 */
	@Override
	public final void typeInField(final String input, final WebElement element,
			final long waitTimeBetweenEntriesMSecs) {

		element.clear();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			String s = new StringBuilder().append(c).toString();
			element.sendKeys(s);
			pageWait(waitTimeBetweenEntriesMSecs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getPath()
	 */
	@Override
	public String getPath() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.seleniumframework.pom.PageObject#scrollIntoView(org.
	 * openqa.selenium.WebElement)
	 */
	public WebElement scrollIntoView(final WebElement element) {
		if (element != null) {
			((JavascriptExecutor) getDriver())
					.executeScript("arguments[0].scrollIntoView(true);", element);
		}
		return element;
	}

}
