package com.governmentcio.seleniumframework.pom;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see AbstractPageObject
 */
public class IndeedHomePageObject extends AbstractPageObject {

	/**
	 * Home page for Indeed home site.
	 */
	private static final String INDEED_HOME_URL = "http://www.indeed.com";

	/**
	 * Job name entry box.
	 */
	@FindBy(id = "text-input-what")
	private WebElement jobName;

	/**
	 * Job location entry box.
	 */
	@FindBy(id = "text-input-where")
	private WebElement jobLocation;

	/**
	 * Button when pressed starts the search function.
	 */
	@FindBy(xpath = "//button[text()='Find Jobs']")
	private WebElement searchButton;

	/**
	 * Represents window frame element.
	 * 
	 */
	@FindBy(css = "body")
	private WebElement body;

	/**
	 * {@link WebDriver} instance that will be initialized further by
	 * {@link AbstractPageObject} constructor. The driver is then navigated to
	 * the home page represented by {@link #getHomeURL()}.
	 * 
	 * @param driver
	 *          {@link WebDriver} which will be initialized by the
	 *          {@link AbstractPageObject} super class.
	 */
	public IndeedHomePageObject(final WebDriver driver) {
		super(driver);
	}

	/**
	 * Clears the {@link #jobName} {@link WebElement} entry field and then enters
	 * the supplied job name value.
	 * 
	 * @param name
	 *          String value of job to enter in the jobName field
	 */
	public final void enterJobEntry(final String name) {
		jobName.clear();
		typeInField(name, jobName);
	}

	/**
	 * Clears the {@link #jobLocation} {@link WebElement} entry field and then
	 * enters the supplied job location value.
	 * 
	 * @param location
	 *          String value of job to enter in the jobLocation field
	 */
	public final void enterJobLocation(final String location) {
		jobLocation.clear();
		typeInField(location, jobLocation);
	}

	/**
	 * Clicks on the searchButton {@link WebElement}.
	 */
	public final void search() {
		searchButton.click();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public final String getHomeURL() {
		return INDEED_HOME_URL;
	}

	public final void createTab() {

		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			// Opens new tab
			body.sendKeys(Keys.CONTROL + "t");
		} else if (os.contains("mac")) {
			// Opens new tab
			body.sendKeys(Keys.COMMAND + "t");
		} else if (os.contains("nux") || os.contains("nix")) {
			// Opens new tab
			body.sendKeys(Keys.CONTROL + "t");
		}

	}

}
