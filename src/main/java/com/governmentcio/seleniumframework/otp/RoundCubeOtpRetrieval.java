package com.governmentcio.seleniumframework.otp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;

public class RoundCubeOtpRetrieval extends AbstractPageObject {
	
		private final static String Mail_Server_PAGE_URL = "http://mail.gov-cio.com";
		
	
		/**
		 * Properties file is used to store static constant value used through out
		 * the application.
		 */
		private static Properties config = new Properties();
	
		/**
		 * Edit box to enter email.
		 */
		@FindBy(id = "rcmloginuser")
		private WebElement textbxEmail;
	
		/**
		 * Password field
		 */
		@FindBy(id = "rcmloginpwd")
		private WebElement passwordField;
	
		/**
		 * Submit Button of Login.
		 */
		@FindBy(id = "rcmloginsubmit")
		private WebElement submitButton;

	
		/**
		 * Inbox object
		 */
		@FindBy(xpath = "//ul[@id='mailboxlist']//a[text()='Inbox']")
		private WebElement inbox;
	
		/**
		 * Message Subject 
		 */
		@FindBy(xpath = "//table[@id='messagelist']//td[@class='subject']/a")
		private WebElement messageSubjectObject;
	
		
		/**
		 * Message content 
		 */
		@FindBy(xpath = "(//*[@class='content-table'])[2]/tbody/tr[1]/td")
		private WebElement messageContent;
		
		/**
		 * Button delete Object
		 */
		@FindBy(xpath = "//a[@class='button delete']")
		private WebElement deleteButton;
		
		/**
		 * CSS of email body.
		 * 
		 */
		@FindBy(css = "body")
		private WebElement body;
		
		/**
		 * @param driver
		 *          Constructor. Creates Attachment page object. Accepts WebDriver
		 *          argument.
		 * @throws IOException 
		 */
		public RoundCubeOtpRetrieval(final WebDriver driver) throws IOException {
			super(driver);
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/data/GSAIAE.properties");
			config.load(fis);
		}
	
		
		public String getOTP() throws InterruptedException {
			
			final String oldTab = getDriver().getWindowHandle();
			this.opneMailerDomain();
			this.loginApp(config.getProperty("username"), config.getProperty("password"));
			
			WebDriverWait waitlogin = new WebDriverWait(getDriver(), 20);
			waitlogin.until(ExpectedConditions.elementToBeClickable(inbox));
			inbox.click();
			waitlogin.until(ExpectedConditions.elementToBeClickable(messageSubjectObject));

			messageSubjectObject.click();
			
			Thread.sleep(500);
			getDriver().switchTo().frame("messagecontframe");
			
			waitlogin.until(ExpectedConditions.elementToBeClickable(messageContent));
			/*
			 * Example email text : "Your OpenAM One Time Password:55680214"
			 */
			Thread.sleep(500);
			final String otpEntryText = messageContent.getText();

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
			
			getDriver().switchTo().defaultContent();
			deleteButton.click();
			
					
			// Closes new tab
			getDriver().close();
			

			// Change focus back to old tab
			try {
				getDriver().switchTo().window(oldTab);
			} catch (UnhandledAlertException noAlert) {
			}

			// Change focus back to original content
			try {
				getDriver().switchTo().defaultContent();
			} catch (UnhandledAlertException noAlert) {
			}

			return otpValue;
		}
		
		
		public String getOTPgoogleVoice() throws InterruptedException {
			
			final String oldTab = getDriver().getWindowHandle();
			this.opneMailerDomain();
			this.loginApp(config.getProperty("username"), config.getProperty("password"));
			
			WebDriverWait waitlogin = new WebDriverWait(getDriver(), 20);
			waitlogin.until(ExpectedConditions.elementToBeClickable(inbox));
			inbox.click();
			waitlogin.until(ExpectedConditions.elementToBeClickable(messageSubjectObject));

			messageSubjectObject.click();
			
			Thread.sleep(500);
			getDriver().switchTo().frame("messagecontframe");
			
			waitlogin.until(ExpectedConditions.elementToBeClickable(messageContent));
			/*
			 * Example email text : "Your OpenAM One Time Password:55680214"
			 */
			Thread.sleep(500);
			final String otpEntryText = messageContent.getText();

			/*
			 * Split the string at the ":".
			 */
			String otpValue = "";

			/*
			 * Grab the second half which is the OTP.
			 */
			if (otpEntryText != null) {
				String[] otpEmailSplit = otpEntryText.trim().split( " ");
				otpValue = otpEmailSplit[0];
			}
			
			getDriver().switchTo().defaultContent();
			deleteButton.click();
			
					
			// Closes new tab
			getDriver().close();
			

			// Change focus back to old tab
			try {
				getDriver().switchTo().window(oldTab);
			} catch (UnhandledAlertException noAlert) {
			}

			// Change focus back to original content
			try {
				getDriver().switchTo().defaultContent();
			} catch (UnhandledAlertException noAlert) {
			}

			return otpValue;
		}
		
		
		/**
		 * Open mail domain application
		 * @throws InterruptedException
		 */
		public void opneMailerDomain() throws InterruptedException {

			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("mac")) {
				// Opens new tab for OSX
				Thread.sleep(5000);
				String a = "window.open('','_blank');";
				((JavascriptExecutor)getDriver()).executeScript(a);
				
				//body.sendKeys(Keys.chord(Keys.CONTROL, Keys.COMMAND, "t"));
			} else if (os.contains("win") || os.contains("nux")
					|| os.contains("nix")) {
				// Opens new tab for Unix or Windows
				String a = "window.open('','_blank');";
				((JavascriptExecutor)getDriver()).executeScript(a);
			}

			// Stores window handles into Array
			ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
			// Switches to new window
			getDriver().switchTo().window(tabs.get(1));			

			getDriver().navigate().to(Mail_Server_PAGE_URL);
			
			WebDriverWait waitlogin = new WebDriverWait(getDriver(), 20);
			waitlogin.until(ExpectedConditions.elementToBeClickable(textbxEmail));
			
		}
	
		/**
		 * Login into mail server application
		 * @param useName
		 * @param password
		 */
		public void loginApp(String useName, String password ){
			textbxEmail.clear();
			textbxEmail.sendKeys(useName);
			passwordField.clear();
			passwordField.sendKeys(password);
			submitButton.click();		
		}
	
	
	
}