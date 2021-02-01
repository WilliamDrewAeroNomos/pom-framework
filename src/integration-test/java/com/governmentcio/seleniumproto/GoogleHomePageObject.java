package com.governmentcio.seleniumproto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;

/**
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see AbstractPageObject
 */
public class GoogleHomePageObject extends AbstractPageObject {

	/**
	 * Home page for Google search site.
	 */
	private static final String GOOGLE_HOME_URL = "http://www.google.com";

	/**
	 * Entry box where search criteria is entered.
	 */
	@FindBy(name = "q")
	private WebElement searchEntryBox;

	/**
	 * Search button which is pressed to activate the search.
	 */
	@FindBy(xpath = ".//*[@id='searchSubmit']")
	private WebElement searchButton;

	/**
	 * {@link WebDriver} instance that will be initialized further by
	 * {@link AbstractPageObject} constructor. The driver is then navigated to
	 * the home page represented by {@link #getHomeURL()}.
	 * 
	 * @param driver
	 *          {@link WebDriver} which will be initialized by the
	 *          {@link AbstractPageObject} super class.
	 */
	public GoogleHomePageObject(final WebDriver driver) {
		super(driver);
	}

	/**
	 * Fills the {@link #searchEntryBox} {@link WebElement} will the searchText
	 * value and then clicks on the {@link #searchButton} {@link WebElement}.
	 * 
	 * @param searchText
	 *          Text value to search for
	 * @return {@link GoogleSearchResultsSeleniumPageObject}
	 */
	public final GoogleSearchResultsSeleniumPageObject search(
			final String searchText) {

		typeInField(searchText, searchEntryBox);

		searchButton.click();

		return new GoogleSearchResultsSeleniumPageObject(getDriver());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public final String getHomeURL() {
		return GOOGLE_HOME_URL;
	}

}
