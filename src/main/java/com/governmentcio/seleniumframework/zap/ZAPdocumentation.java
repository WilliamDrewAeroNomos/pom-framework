package com.governmentcio.seleniumframework.zap;

public class ZAPdocumentation {
	/**
	 * @author kshah@governmentcio.com 
	 * 
	 * 		   Command Line Args -
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpCmdline
	 *         https://github.com/zaproxy/zaproxy/wiki/FAQapikey
	 *         https://github.com/zaproxy/zaproxy/wiki/FAQremote
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpUiDialogsOptionsApi#addresses-permitted-to-use-the-api
	 * 
	 *         Jenkins - 
	 *         https://github.com/zaproxy/zaproxy/wiki Connect ZAP
	 *         remotely https://zaproxy.blogspot.com/
	 * 
	 *         API - 
	 *         https://github.com/zaproxy/zaproxy/wiki/FAQremote API Options -
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpUiDialogsOptionsApi
	 * 
	 *         Active Scan -
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpStartConceptsAscan#active-scan
	 * 
	 *         Passive Scan -
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpStartConceptsPscan#passive-scan
	 * 
	 *         Spidering -
	 *         https://github.com/zaproxy/zap-core-help/wiki/HelpStartConceptsSpider#spider
	 *         
	 *         Note: Functionalities are explained with respective methods
	 *        Jenkins config:
	 *        In order to run scan on a jenkins node/slave, ZAP_ADDRESS = IP address of your jenkins slave/node and ZAP_PORT= port on jenkins node
	 *        Note that jenkins node needs to be inside the same firewall + proxy + internet network for this to work without setting up any proxy.
	 *        
	 *         
	 *        Currently ZAP is configured to run without any api key. While this practice is discouraged, but if your ZAP is inside an organization's secure network it should not pose any security threat. But consider this risk.
	 *        	* If you are not using api key for ZAP, ZAP.ZAP_API_KEY = None
	 *        Currently ZAP is configured to run in deamon mode (no-UI mode). See ZAP.OpenZAP()
	 *         
	 *         
	 *         Passive Scan:
	 *         Passive scanning is ZAP scanning the browser traffic in the background without doing any activity (active scan or spidering) on its own. 
	 *         ZAP just listens to all the traffic. 
	 *         
	 *         Setup/Config required:
	 *        1.  ZAP needs to be downloaded on the machine where you open your browser (This can be a jenkins node).
	 *        2. Do your selenium tests/actions
	 *         * Your seleni:m browser needs to open on the host and port that ZAP is listening on. Host and port are mentioned in ZAP.java
	 *         * ZAP_ADDRESS
	 *         * ZAP_PORT
	 *         * See PassiveScan.setup() on how to open selenium browser on a specified port.
	 *        3. Output zap report to see results. You can alternatively use json/html/xml report to create your own report. See ZAP.getReport() Line 61, Line 62
	 *         
	 *         
	 *         Active Scanning:
	 *         Active scanning works the same way as passive scanning, except you have to provide a url to scan. While passive scanning automatically scans all the traffic on your browser.
	 *         
	 *         
	 *         Spidering:
	 *         Spidering is to analyze the html structure of the webpages. 
	 *         There are two opens:
	 *          * Scan only specifed page
	 *          		* See ZAp.spider() - ApiResponse resp = api.spider.scan(url, null, null, null, null);
	 *          * Scan all pages recursively. This option if enabled, scans recursively all the url inside the gicen url - recursively till it finds all urls. Note that this process can take a lot of time.
	 *          		* See ZAp.spider() - ApiResponse resp = api.spider.scan(url, null, true, null, null);
	 *         
	 *         
	 *         
	 *         
	 *         Setup:
	 *         1. Download latest ZAP cross platform from the url - https://github.com/zaproxy/zaproxy/wiki/Downloads
	 *         2. Unzip ZAP contents in a folder. Make a note of the directory where ZAP is downloaded.
	 *         3. Provide this path in your properties file
	 *     
	 */
	

}