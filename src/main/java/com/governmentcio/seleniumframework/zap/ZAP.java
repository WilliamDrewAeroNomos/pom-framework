package com.governmentcio.seleniumframework.zap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.clientapi.core.ClientApiMain;

import com.governmentcio.seleniumframework.rules.TestReporter;

/**
 * A simple example showing how to use the API to spider and active scan a site and then retrieve
 * and print out the alerts.
 * @author Kashif Shah
 * <p>ZAP must be running on the specified host and port for this script to work
 */

public class ZAP {

	private static final String NO_API_KEY = "api.disablekey=true"; //	https://github.com/zaproxy/zaproxy/wiki/FAQapikey
	private static final String NON_UI_MODE = "-daemon";
	public static String ZAP_ADDRESS = "localhost";
	public static int ZAP_PORT = 8080;
	private static final String ZAP_API_KEY = "None"; //	= "95aat12fikf41g1hfp550m6o6b"; // Change this if you have set the apikey in 	// ZAP via Options / API
	private static ClientApi clientApi = null;
	public static String zapWorkingDirectory = null;
	
	public static void OpenZAP() {
		String zapExecutableLocation = zapWorkingDirectory + "zap.sh";
		ProcessBuilder pb = new ProcessBuilder(zapExecutableLocation, 
				"-config", NO_API_KEY, 
				NON_UI_MODE, 
				"-host",
				ZAP_ADDRESS, 
				"-port", String.valueOf(ZAP_PORT));
		pb.directory(new File(zapWorkingDirectory).getAbsoluteFile());
//		pb.redirectErrorStream(true);
//		pb.redirectOutput(new File("OWASPZAPOutputStream.txt"));
		System.out.println("Trying to invoke the ZAP executable");
		try {
			Process p = pb.start();
			getClientAPI();
		} catch (IOException | ClientApiException e) {
			System.out.println(e);
		}
		System.out.println("Waiting for successful connection to ZAP");
	}

	 private static String getReportFileName() {
		 String zapOutputDir = System.getProperty("user.dir") + "/target/zap/";
		 File outDir = new File(zapOutputDir);
		if (!outDir.exists()) {
			outDir.mkdir();
		}
		 String reportName = "ZAP-"+new SimpleDateFormat("dd-MM-YYYY HH.mm.ss").format(new Date()) + ".html";
	    return zapOutputDir + reportName;
	  }
	public static void getReport() {
		try {
			System.out.println("Records to scan: " + clientApi.pscan.recordsToScan());
			do {
//				System.out.println("Records to scan: " + api.pscan.recordsToScan());
			} while (!"0".equals(clientApi.pscan.recordsToScan().toString()));
			FileOutputStream fos = new FileOutputStream(new File(getReportFileName()));
			fos.write(clientApi.core.htmlreport());
			fos.close();
			System.out.println("ZAP report created successfully");
//			System.out.println(new String(clientApi.core.htmlreport())); // save this as html file to see html report
//			System.out.println(new String(clientApi.core.jsonreport())); // use json to create custom reports
		} catch (ClientApiException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void getClientAPI() throws ClientApiException {
		clientApi = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
		clientApi.waitForSuccessfulConnectionToZap(3600);
		System.out.println("ZAP Started...");
	}

	public static void activescan(String url) throws ClientApiException, InterruptedException {
		ApiResponse resp;
		String scanid;
		int progress;
		System.out.println("Active scan : " + url);
		resp = clientApi.ascan.scan(url, "True", "False", null, null, null);

		// The scan now returns a scan id to support concurrent scanning
		scanid = ((ApiResponseElement) resp).getValue();

		// Poll the status until it completes
		while (true) {
			Thread.sleep(5000);
			progress = Integer.parseInt(((ApiResponseElement) clientApi.ascan.status(scanid)).getValue());
			System.out.println("Active Scan progress : " + progress + "%");
			if (progress >= 100) {
				break;
			}
		}
		System.out.println("Active Scan complete");
	}

	public static void spider(String url) throws ClientApiException, InterruptedException {
		System.out.println("Spider : " + url);
		ApiResponse resp = clientApi.spider.scan(url, null, null, null, null);
		String scanid;
		int progress;

		// The scan now returns a scan id to support concurrent scanning
		scanid = ((ApiResponseElement) resp).getValue();

		// Poll the status until it completes
		while (true) {
			Thread.sleep(1000);
			progress = Integer.parseInt(((ApiResponseElement) clientApi.spider.status(scanid)).getValue());
			System.out.println("Spider progress : " + progress + "%");
			if (progress >= 100) {
				break;
			}
		}
		System.out.println("Spider complete");
		// Give the passive scanner a chance to complete
		Thread.sleep(2000);
	}

	public static String checkErrors(ClientApi api) {
		String errors = "";
		List<Alert> ignoreAlerts = new ArrayList<>(2);
		// ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null,
		// Risk.Low, Reliability.Warning, null, null) {});
		// ignoreAlerts.add(new Alert(null, null, Risk.Low, Reliability.Warning, null,
		// null));
		// ignoreAlerts.add(new Alert(null, null, Risk.Informational,
		// Reliability.Warning, null, null));
		try {
			System.out.println("Checking Alerts...");
			api.checkAlerts(ignoreAlerts, null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			errors = ex.getMessage();
		}
		return errors;
	}
	
	public static void stopZAP(String zapaddr, int zapport) {
		ClientApiMain.main(new String[] { "stop", "zapaddr=" + zapaddr,	"zapport=" + zapport });
	}
	
	public static void saveSaeesion() {
		try {
			clientApi.core.saveSession(System.getProperty("user.dir") + "/" +  "target/zapsessions" + "/" + Calendar.getInstance().getTimeInMillis(), null);
		} catch (ClientApiException e) {
			System.out.println(e);
		}
	}
}