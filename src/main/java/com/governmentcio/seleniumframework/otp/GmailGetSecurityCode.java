package com.governmentcio.seleniumframework.otp;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;

/**
 * This class logins to gmail and gets the OTP security code for the GSA IAE
 * application.
 *
 * @author kshah@governmentcio.com
 * @version 1.0
 * @since 1.0
 *
 */
public class GmailGetSecurityCode extends AbstractPageObject {

	/**
	 * Properties file is used to store static constant value used through out
	 * the application.
	 */
	private static Properties config = new Properties();

	/**
	 * Edit box to enter email.
	 */
	@FindBy(xpath = ".//*[@id='identifierId']")
	private WebElement textbxEmail;

	/**
	 * Next button.
	 */
	@FindBy(xpath = ".//*[@id='identifierNext']/content/span")
	private WebElement userIdentifierNextButton;

	/**
	 * Edit box to enter password.
	 */
	@FindBy(xpath = ".//*[@id='password']/div[1]/div/div[1]/input")
	private WebElement textbxPassword;

	/**
	 * Sign In button.
	 */
	@FindBy(xpath = ".//*[@id='passwordNext']/content/span")
	private WebElement passwordNextButton;

	/**
	 * Sign Out options.
	 */
	@FindBy(xpath = "//a[contains(@href,'SignOutOptions')]")
	private WebElement iconUser;

	/**
	 * Sign Out button.
	 */
	@FindBy(xpath = "//a[.='Sign out']")
	private WebElement btnSignout;

	/**
	 * Gmail Account chooser.
	 * 
	 */
	@FindBy(xpath = ".//a[@id='account-chooser-link']")
	private List<WebElement> lnkAccountChooser;

	/**
	 * Allow account edits.
	 * 
	 */
	@FindBy(xpath = ".//a[@id='edit-account-list']")
	private WebElement lnkRemoveAccount;

	/**
	 * Xpath Gmail Account chooser.
	 * 
	 */
	@FindBy(xpath = ".//button[@id='choose-account-0']")
	private WebElement lnkSelectAccount;

	/**
	 * Xpath for Confirm password.
	 * 
	 */
	@FindBy(xpath = "//input[@id='iae-password-confirm']")
	private WebElement textboxConfirmPassword;

	/**
	 * Xpath for new password.
	 * 
	 */
	@FindBy(xpath = ".//input[@id='iae-reset-password-new']")
	private WebElement textboxNewPassword;

	/**
	 * Xpath of One Time Password link.
	 * 
	 */
	@FindBy(xpath = "//*[.='OpenAM One Time Password']")
	private WebElement lnkEmail;

	/**
	 * Xpath of Forgot Password link.
	 * 
	 */
	@FindBy(xpath = "//a[contains(@data-saferedirecturl,'reset')]")
	private WebElement lnkForgotPassword;

	/**
	 * . Xpath of Open button.
	 * 
	 */
	@FindBy(xpath = "//span [@class='y2' and contains(.,'reset your password.')]")
	private WebElement forgotPasswordEmail;

	/**
	 * OTP email title.
	 * 
	 */
	@FindBy(xpath = "//span [@class='y2' and contains(.,'OpenAM')]")
	private WebElement otpEmailTitle;

	/**
	 * Password Reset Submit button.
	 * 
	 */
	@FindBy(xpath = "/.//div[@id='password-reset']/button")
	private WebElement buttonSubmit;

	/**
	 * Delete button.
	 * 
	 */
	@FindBy(xpath = ".//*[@data-tooltip='Delete']")
	private WebElement btnDelete;

	/**
	 * CSS of email body.
	 * 
	 */
	@FindBy(css = "body")
	private WebElement body;

	private static final int wait05 = 01;

	/**
	 *
	 * @param driver
	 *          Constructor
	 * @throws IOException
	 *           Throws IOException.
	 */
	public GmailGetSecurityCode(final WebDriver driver) throws IOException {
		super(driver);
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/data/GSAIAE.properties");
		config.load(fis);
	}

	/**
	 *
	 * @param driver
	 *          Accepts WebDriver argument.
	 * @return Value of the most recent OTP value sent via email.
	 * @throws InterruptedException
	 *           throws Interrupted Exception.
	 * @throws AWTException 
	 *
	 */
	public final String getOTPValue(final WebDriver driver)
			throws InterruptedException, AWTException {

		final String oldTab = driver.getWindowHandle();

		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("mac")) {
			// Opens new tab for OSX
			String a = "window.open('http://gmail.com','_blank');";
			((JavascriptExecutor)driver).executeScript(a);
		} else if (os.contains("win") || os.contains("nux")
				|| os.contains("nix")) {
			// Opens new tab for Unix or Windows
			body.sendKeys(Keys.CONTROL + "t");
		}

		// Stores window handles into Array
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		// Switches to new window
		driver.switchTo().window(tabs.get(1));
		driver.get(config.getProperty("url"));
		if (!lnkAccountChooser.isEmpty()) {
			removeAccount();
			driver.get(config.getProperty("url"));
		}

		WebDriverWait waitlogin = new WebDriverWait(driver, wait05);

		waitlogin.until(ExpectedConditions.elementToBeClickable(textbxEmail));

		textbxEmail.click();

		// Logs in to Gmail
		gmailLogin(config.getProperty("username"), config.getProperty("password"));

		/*
		 * Example email text : "Your OpenAM One Time Password:55680214"
		 */
		final String otpEntryText = otpEmailTitle.getText();

		/*
		 * Split the string at the ":".
		 */
		String otpValue = "";

		/*
		 * Grab the second half which is the OTP.
		 */
		if (otpEntryText != null) {
			String[] otpEmailSplit = otpEntryText.split(":", 2);
			otpValue = otpEmailSplit[1];
		}

		// Deletes email
		Actions keyboard = new Actions(driver);

		keyboard.contextClick(otpEmailTitle).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ENTER).perform();


		//Wait for email to be deleted
		
		
		//Sign out of Gmail
		signOutOfGmail();

		// Handle alert
		try {
      WebDriverWait wait = new WebDriverWait(driver, 2);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      alert.accept();
		} catch (Exception e) {
      //exception handling
		}
	

		// Close tab
		try {
			if (os.contains("win")) {
				driver.close();
				// Closes new tab
			} else if (os.contains("mac")) {
				driver.close();
				// Closes new tab
			} else if (os.contains("nux") || os.contains("nix")) {
				// Closes new tab
				driver.close();
			}
		} catch (UnhandledAlertException | NoAlertPresentException noAlert) {
		}

		// Change focus back to old tab
		try {
			driver.switchTo().window(oldTab);
		} catch (UnhandledAlertException noAlert) {
		}

		// Change focus back to original content
		try {
			driver.switchTo().defaultContent();
		} catch (UnhandledAlertException noAlert) {
		}

		return otpValue;

	}

	/**
	 * Clicks on the user and sign out buttons to exit GMail.
	 */
	public final void signOutOfGmail() {
		// Signs out of Gmail
		iconUser.click();
		btnSignout.click();
	}

	/**
	 * Deletes any existing OTP email.
	 * 
	 * @param driver
	 *          Accepts WebDriver argument.
	 * @throws InterruptedException
	 *           throws Interrupted Exception.
	 *
	 */
	public final void deleteEmail(final WebDriver driver)
			throws InterruptedException {
		final String oldTab = driver.getWindowHandle();

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

		// Stores window handles into Array
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		// Switches to new window
		driver.switchTo().window(tabs.get(0));
		driver.get(config.getProperty("url"));
		if (!lnkAccountChooser.isEmpty()) {
			removeAccount();
			driver.get(config.getProperty("url"));
		}

		WebDriverWait waitlogin = new WebDriverWait(driver, wait05);

		waitlogin.until(ExpectedConditions.elementToBeClickable(textbxEmail));

		// Logs in to Gmail
		gmailLogin(config.getProperty("username"), config.getProperty("password"));

		// Deletes OTP email if displayed

		try {

			Actions keyboard = new Actions(driver);

			keyboard.contextClick(otpEmailTitle).sendKeys(Keys.ARROW_DOWN)
					.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
					.sendKeys(Keys.ENTER).perform();
		} catch (NoSuchElementException e) {
		}

		WebDriverWait waitLogout = new WebDriverWait(driver, wait05);

		waitLogout.until(ExpectedConditions.elementToBeClickable(iconUser));

		signOutOfGmail();

		// Handle alert
		try {
			WebDriverWait wait = new WebDriverWait(driver, wait05);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (TimeoutException | NoAlertPresentException noAlert) {
		}

		// Close tab
		try {
			if (os.contains("win")) {
				// Closes new tab
				body.sendKeys(Keys.CONTROL + "w");
			} else if (os.contains("mac")) {
				// Closes new tab
				body.sendKeys(Keys.COMMAND + "w");
			} else if (os.contains("nux") || os.contains("nix")) {
				// Closes new tab
				body.sendKeys(Keys.CONTROL + "w");
			}
		} catch (UnhandledAlertException | NoAlertPresentException noAlert) {
		}

		// Change focus back to old tab
		try {
			driver.switchTo().window(oldTab);
		} catch (UnhandledAlertException noAlert) {
		}

		// Change focus back to original content
		try {
			driver.switchTo().defaultContent();
		} catch (UnhandledAlertException noAlert) {
		}

	}

	/**
	 *
	 * @param driver
	 *          Constructor
	 * @param newPassword
	 *          Constructor
	 * 
	 * @throws InterruptedException
	 *           Throws InterruptedException.
	 */
	public final void gmailClickForgotPasswordLink(final WebDriver driver,
			final String newPassword) throws InterruptedException {
		final String oldTab = driver.getWindowHandle();

		// Opens new tab
		body.sendKeys(Keys.CONTROL + "t");
		// Stores window handles into Array
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		// Switches to new window
		driver.switchTo().window(tabs.get(0));
		driver.get(config.getProperty("url"));
		if (lnkAccountChooser.isEmpty()) {
			removeAccount();
			driver.get(config.getProperty("url"));
		}
		// Logs in to Gmail
		gmailLogin(config.getProperty("gmailUsername"),
				config.getProperty("gmailPassword"));

		WebDriverWait wait = new WebDriverWait(driver, wait05);

		// Waits for OTP email to be visible
		wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordEmail));

		forgotPasswordEmail.click();

		lnkForgotPassword.click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		textboxNewPassword.sendKeys(newPassword);
		textboxConfirmPassword.sendKeys(newPassword);

		buttonSubmit.click();

		body.sendKeys(Keys.CONTROL + "w");

		driver.switchTo().window(tabs.get(0));

		driver.navigate().back();

		driver.navigate().refresh();

		// Deletes email
		Actions keyboard = new Actions(driver);

		keyboard.contextClick(forgotPasswordEmail).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
				.sendKeys(Keys.ENTER).perform();

		WebDriverWait waitLogout = new WebDriverWait(driver, wait05);

		waitLogout.until(ExpectedConditions.elementToBeClickable(iconUser));

		signOutOfGmail();

		// Close tab
		body.sendKeys(Keys.CONTROL + "w");

		// Handle alert

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException noAlert) {
		}

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException noAlert) {
		}

		// Change focus back to old tab
		driver.switchTo().window(oldTab);

		driver.switchTo().defaultContent();
		// Trims the subject line to return the OTP password

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

	/**
	 *
	 * Removes existing gmail account.
	 *
	 * @throws InterruptedException
	 *           throws Interrupted Exception.
	 *
	 */

	public final void removeAccount() throws InterruptedException {

		lnkAccountChooser.get(0).click();
		lnkRemoveAccount.click();
		lnkSelectAccount.click();
		lnkRemoveAccount.click();
	}

	/**
	 *
	 * @param username
	 *          Accepts username for gmail.
	 * @param password
	 *          Accepts password for gmail.
	 * @throws InterruptedException
	 *           throws Interrupted Exception.
	 *
	 */
	public final void gmailLogin(final String username, final String password)
			throws InterruptedException {

		typeInField(username, textbxEmail);
		userIdentifierNextButton.click();
		pageWait(2000);
		typeInField(password, textbxPassword);
		passwordNextButton.click();
	}

}