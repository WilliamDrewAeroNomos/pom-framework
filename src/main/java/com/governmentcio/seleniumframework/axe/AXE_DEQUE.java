package com.governmentcio.seleniumframework.axe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deque.axe.AXE;
import com.google.common.io.Files;
import com.governmentcio.seleniumframework.configuration.TestConfigurationData;
import com.governmentcio.seleniumframework.rules.TestCaseRecorder;
import com.relevantcodes.extentreports.LogStatus;

public class AXE_DEQUE {
	
	/**
	 * Class logger.
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private static final URL scriptUrl =
			AXE_DEQUE.class.getResource("/axe.min.js");

	public void testAccessibility(WebDriver driver,
			TestCaseRecorder tr) {
		TestConfigurationData testConf = new TestConfigurationData();
		
		try {
			if(StringUtils.isBlank(System.getProperty("enableaxe"))) {
				testConf.loadAXE();
			}
		} catch (IOException e) {
			LOG.error("Error while loading AXE properties", e);
		}
		
		String enable = System.getProperty("enableaxe");
		String standard = System.getProperty("axestandard");
		if(enable.equalsIgnoreCase("true")) {
			try {
				JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
						.options("{\n" + "  runOnly: {\n" + "    type: \"tag\",\n"
								+ "    values: [\"$text$\"]\n".replace("$text$", standard)
								+ "  }\n" + "}")
						.analyze();
	
				JSONArray violations = responseJSON.getJSONArray("violations");
	
				if (violations.length() >= 0) {
					File folderPath = new File(System.getProperty("user.dir") + "/target/Axe/");
					if(!folderPath.exists()) {
						folderPath.mkdir();
					}
					File axeReport = new File(folderPath.getAbsolutePath() + "/Axe-" + Calendar.getInstance().getTime().toGMTString() + ".txt");
					String reportPath = "<a href='file:///$path$'>Report</a>".replace("$path$", axeReport.getAbsolutePath());
					Files.write(driver.getCurrentUrl() + System.lineSeparator() + AXE.report(violations), axeReport, Charset.forName("UTF-8"));
					TestCaseRecorder.getCurrentTest().log(LogStatus.PASS,"WARNING: Accessibility failed for " + driver.getCurrentUrl() + System.lineSeparator() + "Violations: " + violations.length() + System.lineSeparator()  + reportPath);
				} else {
					TestCaseRecorder.getCurrentTest().log(LogStatus.PASS, "No accessibility errors for: "  + standard);
				}
			} catch (Exception e) {
				LOG.error("Error while running AXE", e);
			}	
		}
		else{
			LOG.info("Axe not scanning");
		}
	}
}
