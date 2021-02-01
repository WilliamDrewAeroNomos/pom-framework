package com.governmentcio.seleniumframework.pom;

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
public class SeleniumDownloadPageObject extends AbstractPageObject {

	/**
	 * Home page for Selenium download site.
	 */
	private static final String SELENIUM_DOWNLOAD_PAGE =
			"http://docs.seleniumhq.org/download/";

	/**
	 * Link to Wiki page for Selenium.
	 */
	@FindBy(xpath = ".//*[@id='mainContent']/p[4]/a")
	private WebElement lnkWikiPage;

	/**
	 * {@link WebDriver} instance that will be initialized further by
	 * {@link AbstractPageObject} constructor. The driver is then navigated to the
	 * home page represented by {@link #getHomeURL()}.
	 * 
	 * @param driver
	 *          {@link WebDriver} which will be initialized by the
	 *          {@link AbstractPageObject} super class.
	 */
	public SeleniumDownloadPageObject(final WebDriver driver) {
		super(driver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.seleniumframework.pom.PageObject#getHomeURL()
	 */
	@Override
	public final String getHomeURL() {
		return SELENIUM_DOWNLOAD_PAGE;
	}

	/**
	 * Clicks on the {@link #lnkWikiPage} {@link WebElement} and returns this
	 * instance.
	 * 
	 * @return {@link SeleniumDownloadPageObject}
	 */
	public final SeleniumDownloadPageObject gotoWikiPage() {

		lnkWikiPage.click();

		return this;
	}

}
