package com.governmentcio.seleniumproto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;
import com.governmentcio.seleniumframework.pom.SeleniumHomePageObject;

/**
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see AbstractPageObject
 */
public class GoogleSearchResultsSeleniumPageObject extends AbstractPageObject {

	/**
	 * Result text from the search with the {@link GoogleHomePageObject}.
	 */
	@FindBy(linkText = "Selenium - Web Browser Automation")
	private WebElement lnkResultListItem;

	/**
	 * {@link WebDriver} instance that will be initialized further by
	 * {@link AbstractPageObject} constructor. The driver is then navigated to the
	 * home page represented by {@link #getHomeURL()}.
	 * 
	 * @param driver
	 *          {@link WebDriver} which will be initialized by the
	 *          {@link AbstractPageObject} super class.
	 */
	public GoogleSearchResultsSeleniumPageObject(final WebDriver driver) {
		super(driver);
	}

	/**
	 * Clicks on the {@link #lnkResultListItem}, instantiates a
	 * {@link SeleniumHomePageObject} using the {@link WebDriver} from
	 * {@link #getDriver()}.
	 * 
	 * @return {@link SeleniumHomePageObject}
	 */
	public final SeleniumHomePageObject getSeleniumHomePageLink() {
		lnkResultListItem.click();
		return new SeleniumHomePageObject(getDriver());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public final String getHomeURL() {
		return "";
	}

}
