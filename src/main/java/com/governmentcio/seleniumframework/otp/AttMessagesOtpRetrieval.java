package com.governmentcio.seleniumframework.otp;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.governmentcio.seleniumframework.pom.AbstractPageObject;

public class AttMessagesOtpRetrieval {
	
		
		private WebDriver driver;
		/**
		 * Properties file is used to store static constant value used through out
		 * the application.
		 */
		private static Properties config = new Properties();
	
		/**
		 * First message element locator
		 */
		@FindBy(xpath = "//section[@class='flex-1 flex-hbox flex-stretch mainContent']//ul/li[2]/div[2]")
		private WebElement firstMessageObj;
	
		/**
		 * Xpath for Validator message.
		 */
		@FindBy(xpath = "//*[@id='userid']")
		private WebElement attusername;
		
		/**
		 * Xpath for Validator message.
		 */
		@FindBy(xpath = "//*[@id='password]")
		private WebElement attpassword;

		/**
		 * Xpath for Validator message.
		 */
		@FindBy(id = "submitButton")
		private WebElement attsubmit;
	
		/**
		 * Xpath of message text
		 */
		@FindBy(xpath="//div[@class='msgBubbleContent']")
		private WebElement messageTextObj;
		
		/**
		 * @param driver
		 *          Constructor. Creates Attachment page object. Accepts WebDriver
		 *          argument.
		 * @throws IOException 
		 */
		public AttMessagesOtpRetrieval(final WebDriver driver) throws IOException {	
			this.driver = driver;
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/data/GSAIAE.properties");
			config.load(fis);
			
		}

		public AttMessagesOtpRetrieval() {}
		
		/**
		 * Utility logs into Att messages web appication and takes OTP
		 * Note: User must set chromeprofilepath with path to local google chrome profile
		 */
		
		
		public String getOTPmobile(String userName, String password) throws InterruptedException {	
			String otp = "";
			String chromeprofilepath = "";
			ChromeOptions options = new ChromeOptions();
		     options.addArguments("--disable-extensions");
		     options.addArguments("user-data-dir=" + chromeprofilepath);
		    options.addArguments("--start-maximized");
		     WebDriver driver = new ChromeDriver(options);	
			
			
		     driver.get("https://messages.att.net");
		     AttMessagesOtpRetrieval objectAtt = PageFactory.initElements(driver, AttMessagesOtpRetrieval.class);
			
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[@id='userid']")).sendKeys(userName);
			driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
			driver.findElement(By.id("submitButton")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//section[@class='flex-1 flex-hbox flex-stretch mainContent']//ul/li[2]/div[2]")).click();
			Thread.sleep(5000);
			String message  = driver.findElement(By.xpath("//div[@class='msgBubbleContent']")).getText();
			driver.close();
			
			if(message != null || ! message.equals(null)) {
				String messageValue[] = message.split(":");
					otp = messageValue[messageValue.length-1]	;
			}
			
			return otp;
		}
		/**
		 * Clicks on "Sign In" link.
		 * 
		 * @return PageObject representing the the IamSignInPO.
		 */
		public final void loginatt() {
			
		}
			
	
}