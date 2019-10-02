package com.cognizant.cloudset.plugins;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesManager {
	
	Logger LOGGER = Logger.getLogger(PropertiesManager.class.getName());
	
	private static final String propertyFileName = "jpaas-integration.properties";
	private static String propertyFile;
	
	private Properties props;
	
	private FileInputStream propInputStream;
	
	public PropertiesManager() {
		try {
			
			LOGGER.info("Reading jpaas-integration properties");
			props = new Properties();
			
			String jenkinsHome = System.getProperty("JENKINS_HOME");
			LOGGER.info("JENKINS HOME IS "+jenkinsHome);
			if(jenkinsHome == null) {
				throw new Exception("JENKINS_HOME not found");
			}
			propertyFile = jenkinsHome+propertyFileName;
			LOGGER.info("Properties file is "+propertyFile);
			
			propInputStream = new FileInputStream(propertyFile);
			props.load(propInputStream);
			LOGGER.info("Successfully loaded properties file: "+propertyFile);
		} catch (FileNotFoundException e) {
			LOGGER.severe("Error loading properties file: "+propertyFile);
		} catch (IOException e) {
			LOGGER.severe("Error loading properties file: "+propertyFile);
		} catch(Exception e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public boolean allowMasterAsSlave() {
		if(props != null) {
			String flag = props.getProperty("allow.master.as.slave");
			return Boolean.parseBoolean(flag);
		}
		return false;
	}
}
