package com.governmentcio.seleniumframework.pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PageObject is the primary interface specification for the Page Object Model
 * (POM) framework. It is implemented by the abstract class
 * {@link AbstractPageObject} which each concrete POM implementation extends to
 * provide the specialized functionality for each application UI page to be
 * included in the workflow and UI testing.
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see AbstractPageObject
 */
public interface PageObject {

	/**
	 * Returns the string representation of the URL for this {@link PageObject}'s
	 * home page including the domain and path.
	 * <p>
	 * 
	 * @return {@link String} representing the URL for this {@link PageObject}'s
	 *         home page
	 */
	String getHomeURL();

	/**
	 * Returns the string representation of the domain portion of the URL for
	 * this {@link PageObject}'s home page.
	 * 
	 * @return {@link String} representing the domain portion of the URL.
	 */
	String getDomain();

	/**
	 * Returns the {@link WebDriver} instance that was used to instantiate the
	 * super class.
	 * <p>
	 * 
	 * @return {@link WebDriver} used by this PageObject
	 */
	WebDriver getDriver();

	/**
	 * Returns the {@link PageObject} representing "home" for this PageObject
	 * implementation. The sub-classing implementation generally will return
	 * reference to this PageObject.
	 * <p>
	 * 
	 * @return {@link PageObject} representing "home"
	 */
	PageObject goHome();

	/**
	 * @return {@link TimeUnit#SECONDS} to wait until a page loads before
	 *         throwing a timeout exception.
	 */
	long getPageloadTimeoutSecs();

	/**
	 * @return {@link TimeUnit#SECONDS} to wait until a {@link WebElement} is
	 *         found before throwing a timeout exception.
	 */
	long getImplicitTimeoutSecs();

	/**
	 * @return {@link TimeUnit#SECONDS} to wait for an asynchronous script to
	 *         finish execution before throwing a timeout exception.
	 */
	long getScriptExecutionTimeoutSecs();

	/**
	 * Returns the time in {@link TimeUnit#MICROSECONDS} between entries of
	 * characters into the target edit control.
	 * 
	 * @return time in {@link TimeUnit#MICROSECONDS} between entries
	 */
	long getWaitTimeBetweenEntriesMSecs();

	/**
	 * Specifies the amount of time in {@link TimeUnit#SECONDS} the driver should
	 * wait when searching for an element if it is not immediately present. If
	 * the element is not present in the page before the timeSecs is exceeded a
	 * {@link TimeoutException} will be thrown.
	 * <p>
	 * 
	 * @param timeInSecs
	 *          Number of {@link TimeUnit#SECONDS}
	 * @return Reference to this {@link PageObject}
	 */
	PageObject setImplicitWaitSecs(long timeInSecs);

	/**
	 * Sets the amount of time to wait for a page load to complete before
	 * throwing a {@link TimeoutException}.
	 * <p>
	 * 
	 * @param timeInSecs
	 *          Number of {@link TimeUnit#SECONDS}
	 * @return Reference to this {@link PageObject}
	 */
	PageObject setPageloadTimeoutSecs(long timeInSecs);

	/**
	 * Sets the amount of time to wait for an asynchronous script to finish
	 * execution before throwing a {@link TimeoutException} returning a reference
	 * to this {@link PageObject}.
	 * <p>
	 * 
	 * @param timeInSecs
	 *          Number of {@link TimeUnit#SECONDS}
	 * @return Reference to this {@link PageObject}
	 */
	PageObject setScriptTimeoutSecs(long timeInSecs);

	/**
	 * Causes the process to wait <code>timeInSecs</code> in
	 * {@link TimeUnit#SECONDS} before proceeding.
	 * <p>
	 * 
	 * @param timeInSecs
	 *          Number of {@link TimeUnit#SECONDS} to wait.
	 */
	void pageWait(final long timeInSecs);

	/**
	 * Waits <code>numberSecs</code> until the <code>webElement</code> is
	 * clickable. Returns the {@link WebElement} that is clicked or throws a
	 * {@link TimeoutException} if <code>numberOfSecs</code> is exceeded.
	 * <p>
	 * 
	 * @param webElement
	 *          {@link WebElement} to be clicked
	 * @param numberOfSecs
	 *          Number of seconds to wait until webElement is clicked.
	 * @return WebElement that was clicked.
	 */
	WebElement waitUntil(WebElement webElement, int numberOfSecs);

	/**
	 * Enters the input string a single character at a time using the
	 * {@link AbstractPageObject#getWaitTimeBetweenEntriesMSecs()} between
	 * entries.
	 * 
	 * @param input
	 *          String of characters to enter into text control.
	 * @param element
	 *          Element affected.
	 */
	void typeInField(final String input, final WebElement element);

	/**
	 * Enters the input string a single character at a time using the
	 * {@link PageObject#getWaitTimeBetweenEntriesMSecs()} between entries.
	 * 
	 * @param input
	 *          String of characters to enter into text control.
	 * @param element
	 *          Element affected.
	 * @param waitTimeBetweenEntriesMSecs
	 *          time in milliseconds between entries.
	 */
	void typeInField(final String input, final WebElement element,
			final long waitTimeBetweenEntriesMSecs);

	/**
	 * Returns the string representation of the path portion of the URL for this
	 * {@link PageObject}'s home page.
	 * 
	 * @return {@link String} representing the path portion of the URL.
	 */
	String getPath();

	/**
	 * Scrolls the {@link WebElement} into view on the browser.
	 * 
	 * @param element
	 *          Element to be moved into view.
	 * @return WebElement passed in to provide fluency.
	 */
	WebElement scrollIntoView(final WebElement element);
}
