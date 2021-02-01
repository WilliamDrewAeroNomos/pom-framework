package com.governmentcio.seleniumframework.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.FileOutputStream;

public class TestHelper {
	
	String path = System.getProperty("user.dir") + "/src/data/testData.properties";
	
		
		//Read Property
		
		public String readValue(String key) throws Exception {
			String value = "";
			try {
				Properties prop = new Properties();
				File f = new File(path);
				if (f.exists()) {
					prop.load(new FileInputStream(f));
					value = prop.getProperty(key);
				} else {
					throw new Exception("File not found");
				}
			} catch (FileNotFoundException ex) {
				System.out.println("Failed to read from application.properties file.");
				throw ex;
			}
			if (value == null)
				throw new Exception("Key not found in properties file");
			return value;
		}
		
		
	
		//writeProperty
		
		public void writeValue(String key, String value) throws IOException {
			
			FileInputStream in = new FileInputStream(path);		
			Properties props = new Properties();
			props.load(in);
			in.close();
		
			FileOutputStream out = new FileOutputStream(path);
			props.setProperty(key, value);
			props.store(out, null);
			out.close();	
			
		}
	

}
