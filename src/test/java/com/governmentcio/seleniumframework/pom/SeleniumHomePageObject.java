package com.governmentcio.seleniumframework.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;
import com.governmentcio.seleniumframework.pom.PageObject;

/**
 * Uses the Selenium home page to test search capabilities and navigation within
 * the site.
 * <p>
 * 
 * @author William Drew (wdrew@governmentcio.com)
 * @version 1.0
 * @since 1.0
 * @see PageObject
 * @see AbstractPageObject
 */
public class SeleniumHomePageObject extends AbstractPageObject {

	/**
	 * Home page for Selenium site.
	 */
	private static final String SELENIUM_HOME_PAGE = "http://www.seleniumhq.org";

	/**
	 * Link to download page.
	 */
	@FindBy(xpath = ".//*[@id='menu_download']/a")
	private WebElement lnkdownloadTab;

	/**
	 * {@link WebDriver} instance that will be initialized further by
	 * {@link AbstractPageObject} constructor. The driver is then navigated to the
	 * home page represented by {@link #getHomeURL()}.
	 * 
	 * @param driver
	 *          {@link WebDriver} which will be initialized by the
	 *          {@link AbstractPageObject} super class.
	 */
	public SeleniumHomePageObject(final WebDriver driver) {
		super(driver);

		getDriver().get(getHomeURL());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public final String getHomeURL() {
		return SELENIUM_HOME_PAGE;
	}

	/**
	 * Clicks the {@link #clickDownloadLink()} {@link WebElement} and returns an
	 * instance of {@link SeleniumDownloadPageObject}.
	 * 
	 * @return {@link SeleniumDownloadPageObject}
	 */
	public final SeleniumDownloadPageObject clickDownloadLink() {

		lnkdownloadTab.click();

		return new SeleniumDownloadPageObject(getDriver());
	}

}
