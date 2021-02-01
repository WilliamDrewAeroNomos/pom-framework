/**
 * 
 */
package com.governmentcio.seleniumframework.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads the properties as name/value pairs that will be used to parameterize
 * the tests.
 * 
 * @author wdrew@governmentcio.com
 * @version 1.0
 * @since 1.0
 * 
 */
public class TestConfigurationData {

	/**
	 * Properties file is used to store static constant value used through out the
	 * application.
	 */
	private Properties configProperties = new Properties();
	
	/**
	 * Default name of the directory containing the configuration property file.
	 */
	private String rootDirectory = "user.dir";
	/**
	 * Name of the sub-directory and file containing the configuration property
	 * file.
	 */
	private String subDirectory = "/src/data/GSAIAE.properties";
	
	/**
	 * 
	 * @param rootDirectoryValue
	 *          Name of the directory containing the configuration property file.
	 * @param subDirectoryValue
	 *          Name of the sub-directory and file containing the configuration
	 *          property file.
	 */
	public TestConfigurationData(final String rootDirectoryValue,
			final String subDirectoryValue) {

		this.rootDirectory = rootDirectoryValue;
		this.subDirectory = subDirectoryValue;
	}

	/**
	 * Instantiated when default values for property file locations are
	 * sufficient.
	 */
	public TestConfigurationData() {
		/**
		 * Intended to be used when the default values for location of the root and
		 * it's sub-directory are used.
		 */
	}

	/**
	 * Loads the property file specified by the composition of
	 * <code>rootDirectory</code> and <code>subDirectory</code>.
	 * 
	 * @throws IOException
	 *           Throws exception if the file name composed of
	 *           <code>rootDirectory</code> and <code>subDirectory</code> is not
	 *           valid.
	 */
	public final void load() throws IOException {
		FileInputStream fis =
				new FileInputStream(System.getProperty(rootDirectory) + subDirectory);
		configProperties.load(fis);
	}
	
	public final void loadZAP() throws IOException {
		Properties zapProperties = new Properties();
		String zapDirectory = "/src/data/ZAP.properties";
		FileInputStream fis =
				new FileInputStream(System.getProperty(rootDirectory) + zapDirectory);
		zapProperties.load(fis);
		for (Object zapProp : zapProperties.keySet()) {
			System.setProperty(zapProp.toString(), zapProperties.get(zapProp).toString());
		}
	}

	public final  void loadAXE() throws IOException {
		Properties axeProperties = new Properties();
		String axeDirectory = "/src/data/AXE.properties";
		FileInputStream fis =
				new FileInputStream(System.getProperty(rootDirectory) + axeDirectory);
		axeProperties.load(fis);
		for (Object axeProp : axeProperties.keySet()) {
			System.setProperty(axeProp.toString(), axeProperties.get(axeProp).toString());
		}
	}
	public final  void loadTestData() throws IOException {
		Properties testDataProperties = new Properties();
		String testData = "/src/data/testData.properties";
		FileInputStream fis =
				new FileInputStream(System.getProperty(rootDirectory) + testData);
		testDataProperties.load(fis);
		for (Object testDataProp : testDataProperties.keySet()) {
			System.setProperty(testDataProp.toString(), testDataProperties.get(testDataProp).toString());
		}
		}

	/**
	 * 
	 * @return The value of <code>rootDirectory</code>.
	 */
	public final String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * 
	 * @return The value of <code>subDirectory</code>.
	 */
	public final String getSubDirectory() {
		return subDirectory;
	}

	/**
	 * Retrieves the value of the named configuration property.
	 * 
	 * @param name
	 *          Name of the configuration property value requested.
	 * @return Value of the named configuration property.
	 */
	public final String getValue(final String name) {
		return configProperties.getProperty(name);
	}
}
