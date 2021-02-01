/**
 * 
 */
package com.governmentcio.seleniumframework.pom.factory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.governmentcio.selenium.listener.EventHandler;
import com.governmentcio.seleniumframework.configuration.TestConfigurationData;
import com.governmentcio.seleniumframework.zap.ZAP;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class creates a WebDriver implementation such as {@link FirefoxDriver}
 * or a {@link RemoteWebDriver} instance depending on the presence of a value
 * for the environment variable SELENIUM_GRID_URL. If there is a value for
 * SELENIUM_GRID_URL, it will be used in constructing a WebDriver instance. An
 * example might be
 * SELENIUM_GRID_URL=http://seleniumhub.devgovcio.com:4444/wd/hub. As the name
 * implies, this would be the instance serving as the hub in a Selenium grid
 * deployment which would then proxy commands to the correct node on the
 * Selenium grid. If there is no value for SELENIUM_GRID_URL then the default
 * FirefoxDriver will be instantiated and all tests will be executed locally.
 * <p>
 * 
 * @author William Drew
 * @version 2.0
 * @since 1.0
 * @see WebDriver
 */
public final class WebDriverFactory {

	/**
	 * Class logger.
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * WebDriver preference value for "blank".
	 */
	private static final String ABOUT_BLANK_PREFERENCE = "about:blank";

	/**
	 * Single instance of the {@link WebDriverFactory} class which is
	 * instantiated at first instantiation of the class.
	 */
	private static final WebDriverFactory WEB_DRIVER_FACTORY =
			new WebDriverFactory();

	/**
	 * 
	 */
	private WebDriverFactory() {
		// do not instantiate
	}

	/**
	 * 
	 * @return Single instance of the {@link WebDriverFactory}
	 */
	public static WebDriverFactory getInstance() {
		return WEB_DRIVER_FACTORY;
	}

	/**
	 * Retrieves the {@link WebDriver} to be used to interact with the target web
	 * site. There are two environment variables used to determine the type of
	 * WebDriver (for example Firefox, Chrome, IE, etc.) and whether it's going
	 * to execute locally or utilize a Selenium Grid installation.
	 * 
	 * @return Either a {@link RemoteWebDriver} or WebDriver implementation
	 *         specified by the value of the environment variable
	 *         "WEBDRIVER_TYPE".
	 * @throws MalformedURLException
	 *           If the environment variable SELENIUM_GRID_URL has been set to a
	 *           malformed URL.
	 */
	public synchronized WebDriver getDriver() throws MalformedURLException {

		WebDriver driver = null;

		
		 
		/**
		 * get the driver type requested by the WEBDRIVER_TYPE env variable or use
		 * the default.
		 * 
		 */
		String webDriverType = System.getenv("WEBDRIVER_TYPE");

		DesiredCapabilities desiredCaps = getDesiredCapabilities(webDriverType);

		/**
		 * Check if user is providing a URL to a Selenium Grid instance
		 */
		String seleniumGridURL = System.getenv("SELENIUM_GRID_URL");

		LOG.info("WEBDRIVER_TYPE = " + webDriverType);
		LOG.info("SELENIUM_GRID_URL = " + seleniumGridURL);

		/**
		 * If the SELENIUM_GRID_URL environment variable present then use the
		 * DesiredCapabilities and create a RemoteWebDriver.
		 */
		if ((null != seleniumGridURL) && (!("".equals(seleniumGridURL)))) {
			driver = new RemoteWebDriver(new URL(seleniumGridURL), desiredCaps);
			LOG.info("Running remotely at [" + seleniumGridURL + "] using ["
					+ desiredCaps.getBrowserName() + "]");
		} else {
			LOG.info("Running browser locally.");

			/**
			 * Creating the appropriate WebDriver type
			 */
			if ((null != webDriverType) && (!("".equals(webDriverType)))) {

				if (webDriverType.equals(BrowserType.CHROME)) {

					LOG.info("started chrome browser");
					/**
					 * TODO: Put these into chrome.properties file.
					 */
					String pathToBinary = "/usr/bin/google-chrome";
					String pathToDriver = getPath() + "//resources//drivers//";
					String os = System.getProperty("os.name");
					LOG.info("started for OS " + os);

					if (os.contains("Windows")) {
						System.setProperty("webdriver.chrome.driver",
								pathToDriver + "chromedriver.exe");
					} else if (os.contains("Mac OS")) {
						System.setProperty("webdriver.chrome.driver",
								pathToDriver + "chromedriver");
					}

					ChromeOptions options = new ChromeOptions();
					options.setBinary(pathToBinary);

					driver = new ChromeDriver();

					LOG.debug("Using ChromeDriver.");

				} else if (webDriverType.equals(BrowserType.EDGE)) {

					driver = new EdgeDriver(DesiredCapabilities.edge());

					LOG.debug("Using EdgeDriver.");

				} else if (webDriverType.equals(BrowserType.FIREFOX)) {

				/*	driver = new FirefoxDriver(getStandardFirefoxProfile());
					((JavascriptExecutor) driver).executeScript("window.focus();");

					LOG.info("Using FirefoxDriver."); */

				} else if (webDriverType.equals(BrowserType.IE)) {

					driver = new InternetExplorerDriver(
							DesiredCapabilities.internetExplorer());

					LOG.debug("Using InternetExplorerDriver.");
				}
			} else {
				/**
				 * webDriverType is null means that WEBDRIVER_TYPE was not specified so
				 * we're going to fall back and use ChromeDriver and a standard profile
				 * as the default.
				 */

				String pathToDriver = getPath() + "//resources//drivers//";
				String os = System.getProperty("os.name");
				LOG.info("started for OS " + os);

				WebDriverManager.chromedriver().setup();
				
				if(StringUtils.isBlank(System.getProperty("enablezap"))) {
					TestConfigurationData config = new TestConfigurationData();
					try {
						config.loadZAP();
					} catch (IOException e) {
						LOG.error("Error while loading zap from properties file", e);
					}
				}
//					enableZap = config.getZapProperties().getProperty("enablezap");
//					proxyaddress = TestConfigurationData.getZapProperties().getProperty("PROXY_ADDRESS");
//					proxyport = TestConfigurationData.getZapProperties().getProperty("PROXY_PORT");
//				} else {
					String enableZap = System.getProperty("enablezap");
					String proxyaddress = System.getProperty("PROXY_ADDRESS");
					String proxyport = System.getProperty("PROXY_PORT");
//				}
				if("true".equals(enableZap)) {
					LOG.info("ZAP is enabled");
					if(StringUtils.isEmpty(proxyaddress)) {
						proxyaddress = "localhost";
					}
					if(StringUtils.isEmpty(proxyport)) {
						proxyport = "8080";
					}
					ZAP.ZAP_ADDRESS = proxyaddress;
					ZAP.ZAP_PORT = Integer.valueOf(proxyport);
					ZAP.zapWorkingDirectory = System.getProperty("ZAP_DIR");
					
					ZAP.OpenZAP();
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability(CapabilityType.PROXY, setupProxy(proxyaddress,proxyport));
					driver = new ChromeDriver(capabilities);
				} else {
					driver = new ChromeDriver();
				}

				LOG.debug(
						"Environment variable WEBDRIVER_TYPE was not defined. Using ChromeDriver as default.");
			}
		}

		EventFiringWebDriver webDriver = new EventFiringWebDriver(driver);
		EventHandler handler = new EventHandler();
		webDriver.register(handler);
		return webDriver;

	}
	
	
	private Proxy setupProxy(String address, String port) {
		Proxy proxy = new Proxy();
		proxy.setProxyType(Proxy.ProxyType.MANUAL);
		proxy.setHttpProxy(address + ":" + port);
		proxy.setFtpProxy(address + ":" + port);
		proxy.setSslProxy(address + ":" + port);
		return proxy;
	}

	/**
	 * Get the DesiredCapabilities for the local or remote web driver requested
	 * via the environment variable WEBDRIVER_TYPE. If the string is empty or no
	 * match is found then ChromeDriver is used as the default if not specified
	 * 
	 * @param webDriverTypeSpecified
	 *          Name of the requested web driver name specified in the
	 *          environment variable WEBDRIVER_TYPE.
	 * @return DesiredCapabilities as specified by the webDriver type.
	 */
	private DesiredCapabilities getDesiredCapabilities(
			final String webDriverTypeSpecified) {

		DesiredCapabilities desiredCaps = DesiredCapabilities.firefox();

		if ((null != webDriverTypeSpecified)
				&& (!("".equals(webDriverTypeSpecified)))) {

			if (webDriverTypeSpecified.equals(BrowserType.CHROME)) {
				desiredCaps = DesiredCapabilities.chrome();
			} else if (webDriverTypeSpecified.equals(BrowserType.EDGE)) {
				desiredCaps = DesiredCapabilities.edge();
			} else if (webDriverTypeSpecified.equals(BrowserType.IE)) {
				desiredCaps = DesiredCapabilities.internetExplorer();
			}
		}

		return desiredCaps;
	}

	/**
	 * Creates a default {@link FirefoxProfile} to pass to the {@link WebDriver}
	 * .
	 * 
	 * @return {@link FirefoxProfile}
	 */
	private FirefoxProfile getStandardFirefoxProfile() {

		String pathToDriver = getPath() + "//resources//drivers//";
		String os = System.getProperty("os.name");
		if (os.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver",
					pathToDriver + "geckodriver.exe");
		} else if (os.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver",
					pathToDriver + "geckodriver");
		}

		java.util.logging.Logger.getLogger("org.openqa.selenium")
				.setLevel(Level.OFF);

		FirefoxProfile prof = new FirefoxProfile();
		prof.setPreference("browser.startup.homepage", ABOUT_BLANK_PREFERENCE);
		prof.setPreference("startup.homepate_welcome_url", ABOUT_BLANK_PREFERENCE);
		prof.setPreference("startup.homepate_welcome_url.additional", "");

		java.util.logging.Logger.getLogger("org.openqa.selenium")
				.setLevel(Level.OFF);
		return prof;
	}

	// Get absolute path
	public String getPath() {
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		String path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	/**
	 * Closes and quits the {@link WebDriver}.
	 * 
	 * @param webDriver
	 *          Valid WebDriver reference.
	 */
	public static synchronized void closeAndQuit(final WebDriver webDriver) {
		if (null != webDriver) {
			webDriver.close();
			webDriver.quit();
		}
	}

}